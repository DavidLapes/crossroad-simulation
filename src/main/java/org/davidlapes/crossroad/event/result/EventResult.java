package org.davidlapes.crossroad.event.result;

import org.davidlapes.crossroad.event.Event;

public class EventResult {

    private Event eventToQueue;
    private EventResultStatus resultStatus;

    public EventResult() {}

    public EventResult(final EventResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public EventResult(final EventResultStatus resultStatus, final Event eventToQueue) {
        this(resultStatus);
        this.eventToQueue = eventToQueue;
    }

    public void setEventToQueue(final Event eventToQueue) {
        this.eventToQueue = eventToQueue;
    }

    public Event getEventToQueue() {
        return eventToQueue;
    }

    public void setResultStatus(final EventResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public EventResultStatus getResultStatus() {
        return resultStatus;
    }
}
