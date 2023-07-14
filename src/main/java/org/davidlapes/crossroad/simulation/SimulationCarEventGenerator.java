package org.davidlapes.crossroad.simulation;

import org.davidlapes.crossroad.Car;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.ArriveEvent;
import org.davidlapes.crossroad.event.Event;

import java.util.ArrayList;
import java.util.List;

import static org.davidlapes.crossroad.simulation.SimulationGeneratorHelper.generateBoundaryNumber;
import static org.davidlapes.crossroad.simulation.SimulationGeneratorHelper.generateRandomExecutionTime;

public class SimulationCarEventGenerator implements SimulationEventGenerator {

    private final int carSpeedLowerBoundary;
    private final int carSpeedHigherBoundary;
    private final int carCountLowerBoundary;
    private final int carCountHigherBoundary;
    private final int simulationDuration;

    public SimulationCarEventGenerator(
            final int carSpeedLowerBoundary,
            final int carSpeedHigherBoundary,
            final int carCountLowerBoundary,
            final int carCountHigherBoundary,
            final int simulationDuration
    ) {
        this.carSpeedLowerBoundary = carSpeedLowerBoundary;
        this.carSpeedHigherBoundary = carSpeedHigherBoundary;
        this.carCountLowerBoundary = carCountLowerBoundary;
        this.carCountHigherBoundary = carCountHigherBoundary;
        this.simulationDuration = simulationDuration;
    }

    private List<Event> generateEvents(
            final RoadOrientation roadOrientation,
            final int currentMinute,
            final int carCount
    ) {
        final List<Event> events = new ArrayList<>();

        for(int i = 0; i < carCount; i++) {
            final int executionTime = generateRandomExecutionTime(currentMinute);
            final Car car = new Car(generateBoundaryNumber(carSpeedLowerBoundary, carSpeedHigherBoundary));
            final Event event = new ArriveEvent(roadOrientation, executionTime, car);
            events.add(event);
        }

        return events;
    }

    @Override
    public List<Event> generateEvents() {
        final List<Event> events = new ArrayList<>();

        for(int currentMinute = 1; currentMinute <= simulationDuration; currentMinute++) {
            final int carCountNorth = generateBoundaryNumber(carCountLowerBoundary, carCountHigherBoundary);
            final int carCountSouth = generateBoundaryNumber(carCountLowerBoundary, carCountHigherBoundary);
            final int carCountEast = generateBoundaryNumber(carCountLowerBoundary, carCountHigherBoundary);
            final int carCountWest = generateBoundaryNumber(carCountLowerBoundary, carCountHigherBoundary);

            final List<Event> northCarEvents = generateEvents(RoadOrientation.NORTH, currentMinute, carCountNorth);
            final List<Event> southCarEvents = generateEvents(RoadOrientation.SOUTH, currentMinute, carCountSouth);
            final List<Event> eastCarEvents = generateEvents(RoadOrientation.EAST, currentMinute, carCountEast);
            final List<Event> westCarEvents = generateEvents(RoadOrientation.WEST, currentMinute, carCountWest);

            events.addAll(northCarEvents);
            events.addAll(southCarEvents);
            events.addAll(eastCarEvents);
            events.addAll(westCarEvents);
        }

        return  events;
    }
}
