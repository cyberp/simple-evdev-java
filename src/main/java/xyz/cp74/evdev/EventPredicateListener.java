package xyz.cp74.evdev;

import java.util.function.Predicate;

public class EventPredicateListener implements EventListener {
    
    EventListener listener;
    Predicate<Integer> predicate;

    public EventPredicateListener(EventListener listener, Predicate<Integer> predicate) {
        this.listener = listener;
        this.predicate = predicate;
    }

	/**
	 * Called if input device generates an event
	 * @param event the event from the device
	 */
    @Override
	public void onEvent(Event event) {
        if (listener!=null && event!=null) {
            if (predicate.test(event.getValue()))
                listener.onEvent(event);
        }
    }	
    
}
