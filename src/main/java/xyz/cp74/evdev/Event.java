package xyz.cp74.evdev;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * The Event
 * 
 * @author Christian Paul
 *
 */
public class Event {

	// attributes
	long seconds;
	long microseconds;
	short type;
	short code;
	int value;
	
	// date formater
	private final static DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
	
	/**
	 * Get the type of the event
	 * @return type as Integer
	 */
	public int getType() {
		return type & 0xffff;
	}
	
	/**
	 * Get the code of the event
	 * @return code as Integer
	 */
	public int getCode() {
		return code & 0xffff;
	}
	
	/**
	 * Get the value of the event
	 * @return value as Integer
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Get the type and code of the event as one value
	 * @param type
	 * @param code
	 * @return typecode
	 */
	protected static int getTypeCode(EventType type, int code) {
		return (type.getId() << 16) + code;
	}

	/**
	 * Get the type and code of the event as one value
	 * @return typecode
	 */
	protected int getTypeCode() {
		return (type << 16) + code;
	}
	
	/**
	 * Get the type of the event
	 * @return type of the event as EventType
	 */
	public EventType getEventType() {
		return EventType.get(type);
	}
	
	/**
	 * Get the seconds part of the timestamp
	 * @return seconds
	 */
	public long getSeconds() {
		return seconds;
	}
	
	/**
	 * Get the microseconds part of the timestamp 
	 * @return microseconds
	 */
	public long getMicroseconds() {
		return microseconds;
	}

	/**
	 * Get the java timestamp of the event
	 * @return timestamp as Date
	 */
	public Date getTimestamp() {
		return new Date(seconds * 1000 + microseconds / 1000);
	}
	
	/**
	 * Check, if key is pressed.
	 * @return true, if key is pressed or value has the value 1
	 */
	public boolean isPressed() {
		return value == KeyState.PRESSED;
	}
	
	/**
	 * Check, if key is released.
	 * @return true, if key is released or value has the value 0
	 */
	public boolean isReleased() {
		return value == KeyState.RELEASED;
	}

	@Override
	public String toString() {
		return df.format(getTimestamp())+" - "+getEventType() + " [" + getCode()+"]: "+getValue(); 
	}
	
}
