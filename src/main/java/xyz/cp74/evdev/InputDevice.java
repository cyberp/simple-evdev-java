package xyz.cp74.evdev;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * 
 * InputDevice
 * 
 * InputDevice read events from input devices provided by the event device (evdev) interface of the linux kernel. 
 * The character devices in the directory /dev/input/eventX are read and decoded. 
 * You can attach a listener to an event with given type and code.
 * 
 * @author Christian Paul
 *
 */
public class InputDevice implements InputDevicePath  {

	// input device path
	private String path = null;
	private Path monitorPath;
	
	// reader thread
	private Thread readerThread;
	private volatile boolean readerRunning;

	// linux event sizes 
	private final static int BUFFER_SIZE = 24;
    private final static int SIZE_32BIT = 16;
	
	// input device path monitor
    private WatchService monitor;
    private Thread monitorThread;
    
    // wait before next connection in milliseconds
    private int connectionDelay = 200;
    private int maxConnectionAttemps = 10;
    
    // thread pool
    ExecutorService threadPool = Executors.newCachedThreadPool();
    
	// listeners
    private Map<Integer, EventListener> eventListeners = new ConcurrentHashMap<Integer, EventListener>();
    private AttachListener attachListener;
    private DetachListener detachListener;
	private EventListener globalListener;
    
	// running state
    private volatile boolean running;

    /**
     * Instantiates new InputDevice.
     * @param path od the input device to read
     */
	public InputDevice(String path) {
		this.path = path;
    }
    
    /**
	 * @return the path of the input device
	 */
	public String getPath() {
		return path;
	}

	/**
     * Set delay before next connecting attempt in milliseconds
     * @param connectionDelay delay in milliseconds
     */
    public void setConnectionDelay(int connectionDelay) {
        this.connectionDelay = connectionDelay;
    }

    /**
     * Get the delay before next connecting attempt in milliseconds
     * @return the delay in milliseconds
     */
    public int getConnectionDelay() {
        return connectionDelay;
    }

    /**
     * Get the maximal attempts of open input device path
     * @return maximal attempts
     */
    public int getMaxConnectionAttemps() {
        return maxConnectionAttemps;
    }

    /**
     * Set the maximal attempts of open input device path
     * @param maxConnectionAttemps maximal attempts
     */
    public void setMaxConnectionAttemps(int maxConnectionAttemps) {
        this.maxConnectionAttemps = maxConnectionAttemps;
    }

    /**
	 * Sets a global listener that listen to all events.
	 * It's only possible to set one global listener.
	 * @param listener instance of EventListener that should receive events
	 */
	public void onEvent(EventListener listener) {
		globalListener = listener;
	}

	/**
	 * Sets an event listener for given event type and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param type the EventType to listen for 
	 * @param code the code of the event
	 * @param listener instance of EventLister that should receive events
	 */
	public void onEvent(EventType type, int code, EventListener listener) {
		if (type != null && listener != null) {
			eventListeners.put(Event.getTypeCode(type, code), listener);
		}
	}

	/**
	 * Sets an event listener for given event type and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param type the EventType to listen for 
	 * @param code the code of the event
     * @param predicate predicate to fulfil
	 * @param listener instance of EventLister that should receive events
	 */
	public void onEvent(EventType type, int code, Predicate<Integer> predicate, EventListener listener) {
		if (type != null && listener != null && predicate!=null) {
			eventListeners.put(Event.getTypeCode(type, code), new EventPredicateListener(listener, predicate));
		}
    }
        
    /**
	 * Sets an event listener for an key event and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param code the code of the event
	 * @param listener instance of EventLister that should receive events
	 */
	public void onKey(int code, EventListener listener) {
		onEvent(EventType.KEY, code, listener);
	}

    /**
	 * Sets an event listener for an key event and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param code the code of the event
     * @param predicate predicate to fulfil
	 * @param listener instance of EventLister that should receive events
	 */
	public void onKey(int code, Predicate<Integer> predicate, EventListener listener) {
		onEvent(EventType.KEY, code, predicate, listener);
	}

    /**
	 * Sets an event listener for an axis event and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param code the code of the event
	 * @param listener instance of EventLister that should receive events
	 */
	public void onAxis(int code, EventListener listener) {
		onEvent(EventType.ABS, code, listener);
	}

    /**
	 * Sets an event listener for an axis event and code.
	 * It's only possible to set one RawListener for one tuple of type and code.
	 * @param code the code of the event
     * @param predicate predicate to fulfil
	 * @param listener instance of EventLister that should receive events
	 */
	public void onAxis(int code, Predicate<Integer> predicate, EventListener listener) {
		onEvent(EventType.ABS, code, predicate, listener);
	}

	/**
	 * Sets an listener to be notfied if the input device is attached.
	 * @param listener AttachedListener to set
	 */
	public void onAttach(AttachListener listener) {
		attachListener = listener;
	}
	
	/**
	 * Sets an listener to be notfied if the input device is detached.
	 * @param listener DetachedListener to set
	 */
	public void onDetach(DetachListener listener) {
		detachListener = listener;
	}
	
	/**
	 * Called if input device is attached.
	 * Calls a added AttachListener and starts reading events from the input device.
	 */
	private void onAttach() {
		
		// start reading thread
		readerThread = new Thread(() -> {
			
            // init buffer
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE).order(ByteOrder.LITTLE_ENDIAN);
            
            // try to open device until max attempts reached or device is open
            InputStream is = null;
            int attempts = 0;
            while (is == null && !readerRunning &&
             attempts < getMaxConnectionAttemps()) {
                try {
                    is = Files.newInputStream(monitorPath, StandardOpenOption.READ);
                    readerRunning = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    // wait some time
                    attempts++;
                    try { Thread.sleep(getConnectionDelay()); } catch (InterruptedException es) {}
                }
            }		
			
			while (readerRunning && is != null) {
	    		try {
	    			// read from input ...
                    buffer.clear();
                    byte bb[] = buffer.array();
                    int size = is.read(bb, 0, BUFFER_SIZE);
                    buffer.put(bb);
  	                buffer.flip();
  	                
  	                // read event
	                Event e = new Event();
	                // 32 bit systems have an event size of 16 bytes (uint32)
	                // 64 bit systems have an event size of 24 bytes (uint64)
	                if (size == SIZE_32BIT) {
	                	e.seconds = buffer.getInt();
	                	e.microseconds = buffer.getInt();
	                } else {
	                 	e.seconds = buffer.getLong();
	                	e.microseconds = buffer.getLong();
	                }
                    e.type = buffer.getShort();
                    // ignore SYN events
                    if (e.type != EventType.SYN.ordinal()) {
	                    e.code = buffer.getShort();
	                    e.value = buffer.getInt();
	                    // send event to listeners
                        if (globalListener!=null) 
                            threadPool.execute(()->globalListener.onEvent(e));
		                if (eventListeners.containsKey(e.getTypeCode()))
			                threadPool.execute(()->eventListeners.get(e.getTypeCode()).onEvent(e));
                    }
	                
	    		} catch (Exception e) {}
	    	}
			
			// close
			if (is!=null) {
				try {
					is.close();
					is = null;
	            } catch (IOException e) {}
			}
			
		});
		readerThread.setName("reader on "+monitorPath.toString());
		readerThread.start();
		
		// send attach event to listener
		if (attachListener != null)
			attachListener.onAttach();
	}
	
	/**
	 * Called if input device is detached.
	 * Calls a added DetachListener and stops reading events from the input device.
	 */
	public void onDetach() {
		// stop reading thread
		readerRunning = false;
		readerThread.interrupt();
		
		// send detach event to listener
		if (detachListener != null)
			detachListener.onDetach();
	}
	
	/**
	 * Starts monitoring and reading the input device.
	 * Runs until ends with finish.
	 */
	public void start() {
		
		running = true;
		
		// init input device path monitor
		try {
			monitor = FileSystems.getDefault().newWatchService();
			monitorPath = FileSystems.getDefault().getPath(path);
			monitorPath.getParent().register(monitor, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			monitor = null;
			running = false;
		}
		
		// start monitor thread
		if (monitor!=null) {
			monitorThread = new Thread(() -> {
				
				// check if pathToWatch already exist
				if (Files.exists(monitorPath))
					onAttach();

				// loops until shutdown
				while (running) {
					WatchKey key;
					try {
						key = monitor.take();
						for (WatchEvent<?> event: key.pollEvents()) {
							
		                    // fast exit 
							WatchEvent.Kind<?> kind = event.kind();
					        if (kind == StandardWatchEventKinds.OVERFLOW) 
					            continue;
					        
					        // analyze event
					        @SuppressWarnings("unchecked")
		                    WatchEvent<Path> ev = (WatchEvent<Path>)event;
					        Path filename = ev.context();
					        Path parent = (Path)key.watchable();
					        Path file = parent.resolve(filename);
					        if (file.equals(monitorPath)) {
					        	// remove directory --> detach device
					        	if (kind == StandardWatchEventKinds.ENTRY_DELETE)
					        		onDetach();
					        	// add directory --> attach device
					        	else if (kind == StandardWatchEventKinds.ENTRY_CREATE)
					        		onAttach();
					        }

						}
						key.reset();
					} catch (InterruptedException e) {}
					
				}
			});
			monitorThread.setName("monitor on "+monitorPath.toString());
			monitorThread.start();
		}
		
	}
	
	/**
	 * Shutdown and finish
	 */
	public void finish() {
		running = false;
        if (monitorThread != null && monitorThread.isAlive())
            monitorThread.interrupt();
        readerRunning = false;
        if (readerThread != null && readerThread.isAlive())
		    readerThread.interrupt();
	}
	
}
