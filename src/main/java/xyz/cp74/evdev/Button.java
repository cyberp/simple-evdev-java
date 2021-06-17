package xyz.cp74.evdev;

/**
 * 
 * Button
 * 
 * Mapping of different button codes.
 * As defined in linux kernel source code.
 * 
 * @author Christian Paul
 * 
 */
public interface Button {
	
	public final static int MISC = 0x100;
	public final static int NUM_0 = 0x100;
	public final static int NUM_1 = 0x101;
	public final static int NUM_2 = 0x102;
	public final static int NUM_3 = 0x103;
	public final static int NUM_4 = 0x104;
	public final static int NUM_5 = 0x105;
	public final static int NUM_6 = 0x106;
	public final static int NUM_7 = 0x107;
	public final static int NUM_8 = 0x108;
	public final static int NUM_9 = 0x109;
	public final static int MOUSE = 0x110;
	public final static int LEFT = 0x110;
	public final static int RIGHT = 0x111;
	public final static int MIDDLE = 0x112;
	public final static int SIDE = 0x113;
	public final static int EXTRA = 0x114;
	public final static int FORWARD = 0x115;
	public final static int BACK = 0x116;
	public final static int TASK = 0x117;
	public final static int JOYSTICK = 0x120;
	public final static int TRIGGER = 0x120;
	public final static int THUMB = 0x121;
	public final static int THUMB2 = 0x122;
	public final static int TOP = 0x123;
	public final static int TOP2 = 0x124;
	public final static int PINKIE = 0x125;
	public final static int BASE = 0x126;
	public final static int BASE2 = 0x127;
	public final static int BASE3 = 0x128;
	public final static int BASE4 = 0x129;
	public final static int BASE5 = 0x12a;
	public final static int BASE6 = 0x12b;
	public final static int DEAD = 0x12f;
	public final static int GAMEPAD = 0x130;
	public final static int SOUTH = 0x130;
	public final static int A = SOUTH;
	public final static int EAST = 0x131;
	public final static int B = EAST;
	public final static int C = 0x132;
	public final static int NORTH = 0x133;
	public final static int X = NORTH;
	public final static int WEST = 0x134;
	public final static int Y = WEST;
	public final static int Z = 0x135;
	public final static int TL = 0x136;
	public final static int TR = 0x137;
	public final static int TL2 = 0x138;
	public final static int TR2 = 0x139;
	public final static int SELECT = 0x13a;
	public final static int START = 0x13b;
	public final static int MODE = 0x13c;
	public final static int THUMBL = 0x13d;
	public final static int THUMBR = 0x13e;
	public final static int DIGI = 0x140;
	public final static int TOOL_PEN = 0x140;
	public final static int TOOL_RUBBER = 0x141;
	public final static int TOOL_BRUSH = 0x142;
	public final static int TOOL_PENCIL = 0x143;
	public final static int TOOL_AIRBRUSH = 0x144;
	public final static int TOOL_FINGER = 0x145;
	public final static int TOOL_MOUSE = 0x146;
	public final static int TOOL_LENS = 0x147;
	public final static int TOOL_QUINTTAP = 0x148;
	public final static int STYLUS3 = 0x149;
	public final static int TOUCH = 0x14a;
	public final static int STYLUS = 0x14b;
	public final static int STYLUS2 = 0x14c;
	public final static int TOOL_DOUBLETAP = 0x14d;
	public final static int TOOL_TRIPLETAP = 0x14e;
	public final static int TOOL_QUADTAP = 0x14f;
	public final static int WHEEL = 0x150;
	public final static int GEAR_DOWN = 0x150;
	public final static int GEAR_UP = 0x151;
	public final static int DPAD_UP = 0x220;
	public final static int DPAD_DOWN = 0x221;
	public final static int DPAD_LEFT = 0x222;
	public final static int DPAD_RIGHT = 0x223;
	public final static int TRIGGER_HAPPY = 0x2c0;
	public final static int TRIGGER_HAPPY1 = 0x2c0;
	public final static int TRIGGER_HAPPY2 = 0x2c1;
	public final static int TRIGGER_HAPPY3 = 0x2c2;
	public final static int TRIGGER_HAPPY4 = 0x2c3;
	public final static int TRIGGER_HAPPY5 = 0x2c4;
	public final static int TRIGGER_HAPPY6 = 0x2c5;
	public final static int TRIGGER_HAPPY7 = 0x2c6;
	public final static int TRIGGER_HAPPY8 = 0x2c7;
	public final static int TRIGGER_HAPPY9 = 0x2c8;
	public final static int TRIGGER_HAPPY10 = 0x2c9;
	public final static int TRIGGER_HAPPY11 = 0x2ca;
	public final static int TRIGGER_HAPPY12 = 0x2cb;
	public final static int TRIGGER_HAPPY13 = 0x2cc;
	public final static int TRIGGER_HAPPY14 = 0x2cd;
	public final static int TRIGGER_HAPPY15 = 0x2ce;
	public final static int TRIGGER_HAPPY16 = 0x2cf;
	public final static int TRIGGER_HAPPY17 = 0x2d0;
	public final static int TRIGGER_HAPPY18 = 0x2d1;
	public final static int TRIGGER_HAPPY19 = 0x2d2;
	public final static int TRIGGER_HAPPY20 = 0x2d3;
	public final static int TRIGGER_HAPPY21 = 0x2d4;
	public final static int TRIGGER_HAPPY22 = 0x2d5;
	public final static int TRIGGER_HAPPY23 = 0x2d6;
	public final static int TRIGGER_HAPPY24 = 0x2d7;
	public final static int TRIGGER_HAPPY25 = 0x2d8;
	public final static int TRIGGER_HAPPY26 = 0x2d9;
	public final static int TRIGGER_HAPPY27 = 0x2da;
	public final static int TRIGGER_HAPPY28 = 0x2db;
	public final static int TRIGGER_HAPPY29 = 0x2dc;
	public final static int TRIGGER_HAPPY30 = 0x2dd;
	public final static int TRIGGER_HAPPY31 = 0x2de;
	public final static int TRIGGER_HAPPY32 = 0x2df;
	public final static int TRIGGER_HAPPY33 = 0x2e0;
	public final static int TRIGGER_HAPPY34 = 0x2e1;
	public final static int TRIGGER_HAPPY35 = 0x2e2;
	public final static int TRIGGER_HAPPY36 = 0x2e3;
	public final static int TRIGGER_HAPPY37 = 0x2e4;
	public final static int TRIGGER_HAPPY38 = 0x2e5;
	public final static int TRIGGER_HAPPY39 = 0x2e6;
	public final static int TRIGGER_HAPPY40 = 0x2e7;

}
