package org.davidlapes;

import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.crossroad.simulation.Simulation;
import org.davidlapes.crossroad.simulation.SimulationBuilder;
import org.davidlapes.crossroad.trafficlight.TrafficLightState;

public class AssignmentBoot {

    public static void main(String[] args) {
        final Simulation simulationBuilder = SimulationBuilder.builder()
                .withSimulationDuration(5)

                .withCarCountLowerBoundary(2)
                .withCarCountHigherBoundary(5)

                .withCarSpeedLowerBoundary(1)
                .withCarSpeedHigherBoundary(3)

                .withNorthSouthRoadTrafficLightState(TrafficLightState.GREEN)
                .withTrafficLightsLowerBoundary(15)
                .withTrafficLightsHigherBoundary(30)
                .withTrafficLightSafetyMultiplier(2)

                .build();

        final CrossroadManager crossroadManager
                = simulationBuilder.generateCrossroadManager();

        crossroadManager.processEvents();
    }
}
