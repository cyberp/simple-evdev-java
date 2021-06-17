package xyz.cp74.evdev;

/**
 * 
 * EventListener listen to events from input devices.
 * 
 * @author Christian Paul
 *
 */
public interface EventListener {

	/**
	 * Called if input device generates an event
	 * @param event the event from the device
	 */
	public void onEvent(Event event);	
	
}
