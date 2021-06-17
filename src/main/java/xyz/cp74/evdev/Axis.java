package xyz.cp74.evdev;

/**
 * 
 * Axis 
 * 
 * Mapping of different axis codes.
 * As defined in linux kernel source code.
 * 
 * @author Christian Paul
 * 
 */
public interface Axis {

	public final static int X = 0x00;
	public final static int Y = 0x01;
	public final static int Z = 0x02;
	public final static int RX = 0x03;
	public final static int RY = 0x04;
	public final static int RZ = 0x05;
	public final static int THROTTLE = 0x06;
	public final static int RUDDER = 0x07;
	public final static int WHEEL = 0x08;
	public final static int GAS = 0x09;
	public final static int BRAKE = 0x0a;
	public final static int HAT = 0x10;
	public final static int HAT0Y = 0x11;
	public final static int HAT1X = 0x12;
	public final static int HAT1Y = 0x13;
	public final static int HAT2X = 0x14;
	public final static int HAT2Y = 0x15;
	public final static int HAT3X = 0x16;
	public final static int HAT3Y = 0x17;
	public final static int PRESSURE = 0x18;
	public final static int DISTANCE = 0x19;
	public final static int TILT_X = 0x1a;
	public final static int TILT_Y = 0x1b;
	public final static int TOOL_WIDTH = 0x1c;
	public final static int VOLUME = 0x20;
	public final static int MISC = 0x28;
	public final static int RESERVED = 0x2e;
	public final static int MT_SLOT = 0x2f;
	public final static int MT_TOUCH_MAJOR = 0x30;
	public final static int MT_TOUCH_MINOR = 0x31;
	public final static int MT_WIDTH_MAJOR = 0x32;
	public final static int MT_WIDTH_MINOR = 0x33;
	public final static int MT_ORIENTATION = 0x34;
	public final static int MT_POSITION_X = 0x35;
	public final static int MT_POSITION_Y = 0x36;
	public final static int MT_TOOL_TYPE = 0x37;
	public final static int MT_BLOB_ID = 0x38;
	public final static int MT_TRACKING_ID = 0x39;
	public final static int MT_PRESSURE = 0x3a;
	public final static int MT_DISTANCE = 0x3b;
	public final static int MT_TOOL_X = 0x3c;
	public final static int MT_TOOL_Y = 0x3d;

}
