# Simple evdev for Java

Simple reader of Linux input device events (evdev) for Java.

This library for java provies a reader to read events from linux input devices like a keyboard, mouse or gamepad.  
The events are read directly from the pathes */dev/input/event[0..n]*. You need read access to the path to use this library. It should work both on 32 bit an 64 bit systems.

This project was created by the needs of having a simple tool to use an Xbox One Controller as input devices in projects. It was inspired by [evdev-java](https://github.com/progman32/evdev-java) library.

## Maven Dependency

To use in maven projects as dependecy:

	<dependency>
	  <groupId>xyz.cp74</groupId>
	  <artifactId>evdev</artifactId>
	  <version>1.0</version>
	</dependency>

## Using the library

To read events instantiate a *InputDevice* object with the path to read for. 

	InputDevice device = new InputDevice("/input/dev/event0");
	
You can use predefined pathes too:

	InputDevice device = new InputDevice(InputDevice.EVENT0);

There are different listeners for events that could occur.
The *AttachListener* and *DetachListener* listen if the device is attached or detached from the system. 

	device.onAttach(() -> System.out.println("device on "+device.getPath()+ " is attached"));
	device.onDetach(() -> System.out.println("device on "+device.getPath()+ " is detached"));

The *EventListener* listen for events like a keystroke from the input device.
You can set on global listener, that listen for all events (except the SYN event type).  
This could be useful for debugging purposes:

	device.onEvent(System.out::println);

Additional you can set one and only one *EventListener* for each combination of event type and code.  
Listen to events from key *A*:

	device.onEvent(EventType.KEY, 30, (ev) -> System.out.println(ev));
	
You should see the output of the listener like that:


The event codes differs an depents on the type of the event. You could use the predfined codes in the interfaces *Key*, *Axis*, *Button*, *XboxOneControllerBT* and *XboxOneControllerUSB*. After implementation or static import of a suitable interface you can write:

	device.onEvent(EventType.KEY, A, (ev) -> System.out.println(ev));

For the event types *EventType.KEY* and *EventType.AXIS* exist methods for comfort using:

	device.onKey(A, (ev) -> System.out.println(ev));

Please note that a keystroke generates two events with different states. One event if the key or button is pressed and one event after releasing the key. The third state is when you press a key repeated.

It's possible to use a *EventPredicateListener*, that wrappes a *EventListener* and notify only about events if a given predicate is fullfilled. That could be that the state of the events is pressed or a gamepad axis is about or lower a value. 

Listen only to key A when it's pressed (value of 1):

	device.onKey(A, (val) -> val == 1, (ev) -> System.out.println(ev));

Use the defined predicates in *KeyState*:

	device.onKey(A, KeyState.isPressed, (ev) -> System.out.println(ev));
	
Finally you have to start the listening of the events from the input device.  
You start listening with:

	device.start();
	
Stop listening:

	device.finish();
	
It's possible that Java can't open the path of the input device because linux don't give you the access rights fast enough. This could happen especially if the device is detached and then attached again.
The library tries to open the path for reading and start another attempt after a delay if the opening fails. You could the maximal attempts and the delay with 

	device.setMaxConnectionAttemps(10);
	device.setConnectionDelay(200);
	
You can try the examples in *Demo.java* or for using the Xbox One Controller *DemoXbox.java*.




	
	