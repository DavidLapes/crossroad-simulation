package org.davidlapes.crossroad.simulation;

import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.Event;
import org.davidlapes.crossroad.event.SwapLightsEvent;
import org.davidlapes.crossroad.trafficlight.TrafficLightState;

import java.util.ArrayList;
import java.util.List;

import static org.davidlapes.crossroad.simulation.SimulationGeneratorHelper.generateBoundaryNumber;

public class SimulationTrafficEventGenerator implements SimulationEventGenerator {

    private final TrafficLightState northSouthRoadTrafficLightState;
    private final int trafficLightsLowerBoundary;
    private final int trafficLightsHigherBoundary;
    private final int trafficLightSafetyMultiplier;
    private final int simulationDuration;

    public SimulationTrafficEventGenerator(
            final TrafficLightState northSouthRoadTrafficLightState,
            final int trafficLightsLowerBoundary,
            final int trafficLightsHigherBoundary,
            final int trafficLightSafetyMultiplier,
            final int simulationDuration
    ) {
        this.northSouthRoadTrafficLightState = northSouthRoadTrafficLightState;
        this.trafficLightsLowerBoundary = trafficLightsLowerBoundary;
        this.trafficLightsHigherBoundary = trafficLightsHigherBoundary;
        this.trafficLightSafetyMultiplier = trafficLightSafetyMultiplier;
        this.simulationDuration = simulationDuration;
    }

    public List<Integer> generateExecutionTimes() {
        final List<Integer> executionTimes = new ArrayList<>();

        int executionTime = 0;

        while (executionTime < simulationDuration * 60 * trafficLightSafetyMultiplier) {
            final int swapTimer = generateBoundaryNumber(
                    trafficLightsLowerBoundary,
                    trafficLightsHigherBoundary
            );

            executionTime += swapTimer;

            executionTimes.add(executionTime);
        }

        return executionTimes;
    }

    @Override
    public List<Event> generateEvents() {
        final List<Integer> executionTimes = generateExecutionTimes();

        final List<Event> events = new ArrayList<>();

        for(final int executionTime : executionTimes) {
            final Event northSwitchEvent = new SwapLightsEvent(RoadOrientation.NORTH, executionTime);
            final Event southSwitchEvent = new SwapLightsEvent(RoadOrientation.SOUTH, executionTime);
            final Event westSwitchEvent = new SwapLightsEvent(RoadOrientation.WEST, executionTime);
            final Event eastSwitchEvent = new SwapLightsEvent(RoadOrientation.EAST, executionTime);

            events.add(northSwitchEvent);
            events.add(southSwitchEvent);
            events.add(westSwitchEvent);
            events.add(eastSwitchEvent);
        }

        return events;
    }

    public TrafficLightState getNorthSouthRoadTrafficLightState() {
        return northSouthRoadTrafficLightState;
    }
}
