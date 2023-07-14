package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.Car;
import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.resolver.CrossroadEventResolver;
import org.davidlapes.crossroad.event.resolver.SimpleCrossroadEventResolver;
import org.davidlapes.crossroad.event.result.EventResult;
import org.davidlapes.crossroad.event.result.EventResultStatus;

public class ArriveEvent implements Event {

    private final long executionTime;
    private final RoadOrientation roadOrientation;
    private final Car car;

    public ArriveEvent(final RoadOrientation roadOrientation, final long executionTime, final Car car) {
        if (roadOrientation == null) {
            throw new IllegalArgumentException("Road orientation can not be null");
        }

        if (car == null) {
            throw new IllegalArgumentException("Arriving car can not be null");
        }

        this.executionTime = executionTime;
        this.roadOrientation = roadOrientation;
        this.car = car;
    }

    @Override
    public EventResult processEvent(final Crossroad crossroad) {
        final CrossroadEventResolver eventResolver = new SimpleCrossroadEventResolver();

        final Road road = eventResolver.resolve(crossroad, this);

        road.processArrive(car);
        return new EventResult(
                EventResultStatus.SUCCESS,
                new EnterCrossroadEvent(roadOrientation, executionTime)
        );
    }

    @Override
    public RoadOrientation getRoadOrientation() {
        return roadOrientation;
    }

    @Override
    public long getExecutionTime() {
        return this.executionTime;
    }

    @Override
    public void reschedule() {
        // Does not need reschedule
    }

    @Override
    public int compareTo(final Event compareTo) {
        return Long.compare(this.executionTime, compareTo.getExecutionTime());
    }
}
