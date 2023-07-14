package org.davidlapes.crossroad;

import org.davidlapes.crossroad.event.Event;
import org.davidlapes.crossroad.event.result.EventResult;
import org.davidlapes.crossroad.event.result.EventResultStatus;

import java.util.List;

public class CrossroadManager {

    private final EventQueue eventQueue;
    private final Crossroad crossroad;

    public CrossroadManager(final Crossroad crossroad) {
        if (crossroad == null) {
            throw new IllegalArgumentException("Crossroad can not be null");
        }

        this.crossroad = crossroad;
        this.eventQueue = new EventQueue();
    }

    public Crossroad getCrossroad() {
        return crossroad;
    }

    public void addEvent(final Event event) {
        eventQueue.enqueue(event);
    }

    public void addEvents(final List<Event> events) {
        for(final Event event : events) {
            addEvent(event);
        }
    }

    private void processEvent(final Event event) {
        final EventResult eventResult = event.processEvent(crossroad);

        final Event eventToQueue = eventResult.getEventToQueue();

        if (eventToQueue != null) {
            if (eventResult.getResultStatus() == EventResultStatus.FAILED) {
                eventToQueue.reschedule();
            }

            eventQueue.enqueue(eventToQueue);
        }
    }

    public void processNext() {
        final Event event = eventQueue.getNext();
        processEvent(event);
    }

    public void processEvents() {
        while (eventQueue.hasEvents()) {
            processNext();
        }
    }
}
