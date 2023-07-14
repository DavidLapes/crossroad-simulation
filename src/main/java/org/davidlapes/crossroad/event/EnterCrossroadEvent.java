package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.Car;
import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.resolver.CrossroadEventResolver;
import org.davidlapes.crossroad.event.resolver.SimpleCrossroadEventResolver;
import org.davidlapes.crossroad.event.result.EventResult;
import org.davidlapes.crossroad.event.result.EventResultStatus;

public class EnterCrossroadEvent implements Event {

    private long executionTime;
    private final RoadOrientation roadOrientation;

    public EnterCrossroadEvent(final RoadOrientation roadOrientation, final long executionTime) {
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

        if (road.isEmpty()) {
            throw new RuntimeException("Illegal state. There is no car waiting on the road.");
        }

        if (road.getTrafficLight().isGreen() && road.isFree()) {
            eventResult.setResultStatus(EventResultStatus.SUCCESS);
            final Car car = road.processEnterCrossroad();

            eventResult.setEventToQueue(new LeaveCrossroadEvent(
                    roadOrientation,
                    executionTime + car.getEscapeTime()
            ));
        } else {
            eventResult.setResultStatus(EventResultStatus.FAILED);
            eventResult.setEventToQueue(this);
        }

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
    public void reschedule() {
        executionTime += 10;
    }

    @Override
    public int compareTo(final Event compareTo) {
        return Long.compare(this.executionTime, compareTo.getExecutionTime());
    }
}
