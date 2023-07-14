package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.resolver.CrossroadEventResolver;
import org.davidlapes.crossroad.event.resolver.SimpleCrossroadEventResolver;
import org.davidlapes.crossroad.event.result.EventResult;
import org.davidlapes.crossroad.event.result.EventResultStatus;

public class SwapLightsEvent implements Event {

    private final long executionTime;
    private final RoadOrientation roadOrientation;

    public SwapLightsEvent(final RoadOrientation roadOrientation, final long executionTime) {
        if (roadOrientation == null) {
            throw new IllegalArgumentException("Road orientation can not be null");
        }

        this.executionTime = executionTime;
        this.roadOrientation = roadOrientation;
    }

    @Override
    public EventResult processEvent(final Crossroad crossroad) {
        final CrossroadEventResolver eventResolver = new SimpleCrossroadEventResolver();

        final Road road = eventResolver.resolve(crossroad, this);

        road.processLightSwitch();
        return new EventResult(EventResultStatus.SUCCESS);
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
    public void reschedule() {
        // Does not need reschedule
    }

    @Override
    public int compareTo(final Event compareTo) {
        return Long.compare(executionTime, compareTo.getExecutionTime());
    }
}
