package xyz.cp74.evdev;

import java.util.function.Predicate;

/**
 * 
 * KeyStates are the possible values of an event from a key. 
 * 
 * @author Christian Paul
 *
 */
public interface KeyState {

    public final static int RELEASED = 0;
    public final static int PRESSED = 1;
    public final static int REPEATED = 2;

    public final static Predicate<Integer> isPressed = (val) -> val == PRESSED;
    public final static Predicate<Integer> isReleased = (val) -> val == RELEASED;
    public final static Predicate<Integer> isRepeated = (val) -> val == REPEATED;
    
}
