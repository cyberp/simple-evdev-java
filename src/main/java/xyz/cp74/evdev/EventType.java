package xyz.cp74.evdev;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Enumeration of different event types.
 * As defined in linux kernel source code.
 *  * 
 * @author Christian Paul
 *
 */
public enum EventType {
	
	SYN(0x00), 
	KEY(0x01), 
	REL(0x02), 
	ABS(0x03), 
	MSC(0x04), 
	SW(0x05),
	LED(0x11),
	SND(0x12),
	REP(0x14),
	FF(0x15),
	PWR(0x16),
	FF_STATUS(0x17);
	
    // type id
	private int id;
	
	// index
	private final static Map<Integer, EventType> index = new HashMap<Integer, EventType>();
	static {
		for (EventType a: EventType.values())
			index.put(a.getId(), a);
	}
	
	/**
	 * Constructor
	 * @param id
	 */
	EventType(int id) {
		this.id = id;
	}
	
	/**
	 * Get the id of the EventType
	 * @return id 
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get EventType with given id
	 * @param id
	 * @return EventType
	 */
	public static EventType get(int id) {
		return index.get(id);
	}

}

