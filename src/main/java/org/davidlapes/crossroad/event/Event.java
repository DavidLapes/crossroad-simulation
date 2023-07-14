package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.result.EventResult;

public interface Event extends Comparable<Event> {

    EventResult processEvent(Crossroad crossroad);
    RoadOrientation getRoadOrientation();
    long getExecutionTime();
    void reschedule();
}
