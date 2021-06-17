package xyz.cp74.evdev;

// for connection of Xbox One Controller through USB port use:
// public class Demo2 implements XboxOneControllerUSB {
// for connection of Xbox One Controller though Bluetooth use:
public class DemoXbox implements XboxOneControllerBT {

	public static void main(String[] args) {
		
		// instantiate InputDevice object
		InputDevice xbox = new InputDevice(InputDevice.EVENT0);
		
		// listen to attach or detaches of the device
		xbox.onAttach(() -> System.out.println("xbox one controller attached"));
		xbox.onDetach(() -> System.out.println("xbox one controller  detached"));
		
		// listen to all events
		// xbox.onEvent(System.out::println);
        
		// listen to xbox one controller A, B, X, Y
		xbox.onKey(A, (ev) -> System.out.println(ev));
		xbox.onKey(B, (ev) -> System.out.println(ev));
		xbox.onKey(X, (ev) -> System.out.println(ev));
		xbox.onKey(Y, (ev) -> System.out.println(ev));

		// listen to right stick in x direction
		xbox.onAxis(RS_X, (ev) -> System.out.println(ev));
		
		xbox.start();

	}

}
