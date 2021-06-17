package xyz.cp74.evdev;

/**
 * 
 * XboxOneControllerUSB
 * 
 * Mapping of alle button and axis codes from the xpad kernel driver if a Xbox One controller is plugged by USB.
 * 
 * @author Christian Paul
 *
 */
public interface XboxOneControllerUSB {

	// button
	public final static int A = 304;		// A green
	public final static int B = 305;		// B red
	public final static int X = 307;		// X blue
	public final static int Y = 308;		// Y yellow
	public final static int MENU = 315;		// menu
	public final static int VIEW = 314;		// view
	public final static int XBOX = 316;  	// home / xbox
	public final static int LS_B = 317;		// left stick button
	public final static int RS_B = 318;		// right stick button
	public final static int LB = 310;		// left bumper
	public final static int RB = 311;		// right bumper
	
	// axis
    public final static int DPAD_X = 16;	// direction pad x
    public final static int DPAD_Y = 17;	// direction pad y
    public final static int LT = 2;			// left trigger
    public final static int RT = 5;			// right trigger
    public final static int RS_X = 3;		// right stick x
    public final static int RS_Y = 4;		// right stick y
    public final static int LS_X = 0;		// left stick x
    public final static int LS_Y = 1;		// left stick y

}
