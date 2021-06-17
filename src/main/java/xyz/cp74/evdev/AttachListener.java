package xyz.cp74.evdev;

/**
 * 
 * AttachListener listen to attaches of input devices.
 * 
 * @author Christian Paul
 *
 */
public interface AttachListener {

	/**
	 * Called if input device is attached   
	 */
	public void onAttach();
	
}
