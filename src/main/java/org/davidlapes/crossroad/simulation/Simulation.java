package org.davidlapes.crossroad.simulation;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Crossroad.CrossroadBuilder;
import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.crossroad.event.Event;
import org.davidlapes.crossroad.trafficlight.TrafficLightState;

import java.util.List;

public class Simulation {

    private final SimulationTrafficEventGenerator trafficEventGenerator;
    private final SimulationCarEventGenerator carEventGenerator;

    public Simulation(
            final SimulationTrafficEventGenerator trafficEventGenerator,
            final SimulationCarEventGenerator carEventGenerator
    ) {
        this.trafficEventGenerator = trafficEventGenerator;
        this.carEventGenerator = carEventGenerator;
    }

    private List<Event> generateEvents() {
        final List<Event> trafficEvents = trafficEventGenerator.generateEvents();
        final List<Event> carEvents = carEventGenerator.generateEvents();

        carEvents.addAll(trafficEvents);

        return carEvents;
    }

    public CrossroadManager generateCrossroadManager() {
        final TrafficLightState northSouthRoadTrafficLightState
                = trafficEventGenerator.getNorthSouthRoadTrafficLightState();

        final TrafficLightState eastWestRoadTrafficLightState;

        if (northSouthRoadTrafficLightState == TrafficLightState.GREEN) {
            eastWestRoadTrafficLightState = TrafficLightState.RED;
        } else {
            eastWestRoadTrafficLightState = TrafficLightState.GREEN;
        }

        final Crossroad crossroad = CrossroadBuilder.getBuilder()
                .withCommonTrafficLightStates(northSouthRoadTrafficLightState, eastWestRoadTrafficLightState)
                .build();

        final CrossroadManager crossroadManager = new CrossroadManager(crossroad);
        crossroadManager.addEvents(generateEvents());

        return crossroadManager;
    }
}
