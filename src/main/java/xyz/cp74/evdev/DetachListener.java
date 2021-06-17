package xyz.cp74.evdev;

/**
 * 
 * DetachListener listen to detaches of input devices.
 * 
 * @author Christian Paul
 *
 */
public interface DetachListener {

	/**
	 * Called if input device is detached   
	 */
	public void onDetach();
	
}
