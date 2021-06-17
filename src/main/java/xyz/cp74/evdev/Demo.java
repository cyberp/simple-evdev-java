package xyz.cp74.evdev;

public class Demo implements Key {

	public static void main(String[] args) {
		
		// instantiate InputDevice object
		InputDevice device = new InputDevice("/dev/input/event0");
		
		// maximal attempts to open
		// delay after failed open 
		// device.setMaxConnectionAttemps(10);
		// device.setConnectionDelay(200);
		
		// use prefined pathes
		// InputDevice device = new InputDevice(InputDevice.EVENT0);
		
		// listen to attach or detaches of the device
		device.onAttach(() -> System.out.println("device on "+device.getPath()+ " is attached"));
		device.onDetach(() -> System.out.println("device on "+device.getPath()+ " is detached"));
		
		// listen to all events
		device.onEvent(System.out::println);
        
        // listen to A 
		device.onEvent(EventType.KEY, 30, (ev) -> System.out.println(ev));
		
		// listen to A (implements interface Key)
		// device.onEvent(EventType.KEY, A, (ev) -> System.out.println(ev));
		
		// listen to A
		// device.onKey(A, (ev) -> System.out.println(ev));
		
		// listen to A if pressed
		// device.onKey(A, (val) -> val == 1, (ev) -> System.out.println(ev));
		// device.onKey(A, KeyState.isPressed, (ev) -> System.out.println(ev));

		// start listening
		device.start();
		
		// stop listening
		// device.finish();

	}

}
