package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.resolver.CrossroadEventResolver;
import org.davidlapes.crossroad.event.resolver.SimpleCrossroadEventResolver;
import org.davidlapes.crossroad.event.result.EventResult;
import org.davidlapes.crossroad.event.result.EventResultStatus;

public class LeaveCrossroadEvent implements Event {

    private final long executionTime;
    private final RoadOrientation roadOrientation;

    public LeaveCrossroadEvent(
            final RoadOrientation roadOrientation,
            final long executionTime
    ) {
        if (roadOrientation == null) {
            throw new IllegalArgumentException("Road orientation can not be null");
        }

        this.executionTime = executionTime;
        this.roadOrientation = roadOrientation;
    }

    @Override
    public EventResult processEvent(final Crossroad crossroad) {
        final EventResult eventResult = new EventResult();

        final CrossroadEventResolver eventResolver = new SimpleCrossroadEventResolver();

        final Road road = eventResolver.resolve(crossroad, this);

        if (road.isFree()) {
            throw new RuntimeException("Road is empty, but should not be.");
        }

        road.processLeaveCrossroad();

        eventResult.setResultStatus(EventResultStatus.SUCCESS);

        return eventResult;
    }

    @Override
    public RoadOrientation getRoadOrientation() {
        return roadOrientation;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public void reschedule() {}

    @Override
    public int compareTo(final Event compareTo) {
        return Long.compare(this.executionTime, compareTo.getExecutionTime());
    }
}
