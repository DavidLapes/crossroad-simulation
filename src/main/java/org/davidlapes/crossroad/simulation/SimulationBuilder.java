package org.davidlapes.crossroad.simulation;

import org.davidlapes.crossroad.trafficlight.TrafficLightState;

public class SimulationBuilder {
    private TrafficLightState northSouthRoadTrafficLightState;
    private int trafficLightsLowerBoundary;
    private int trafficLightsHigherBoundary;
    private int trafficLightSafetyMultiplier;
    private int carSpeedLowerBoundary;
    private int carSpeedHigherBoundary;
    private int carCountLowerBoundary;
    private int carCountHigherBoundary;
    private int simulationDuration;

    private SimulationBuilder() {}

    public SimulationBuilder withNorthSouthRoadTrafficLightState(
            final TrafficLightState northSouthRoadTrafficLightState
    ) {
        this.northSouthRoadTrafficLightState = northSouthRoadTrafficLightState;
        return this;
    }

    public SimulationBuilder withTrafficLightsLowerBoundary(final int seconds) {
        this.trafficLightsLowerBoundary = seconds;
        return this;
    }

    public SimulationBuilder withTrafficLightsHigherBoundary(final int seconds) {
        this.trafficLightsHigherBoundary = seconds;
        return this;
    }

    public SimulationBuilder withTrafficLightSafetyMultiplier(final int safetyMultiplier) {
        this.trafficLightSafetyMultiplier = safetyMultiplier;
        return this;
    }

    public SimulationBuilder withCarSpeedLowerBoundary(final int seconds) {
        this.carSpeedLowerBoundary = seconds;
        return this;
    }

    public SimulationBuilder withCarSpeedHigherBoundary(final int seconds) {
        this.carSpeedHigherBoundary = seconds;
        return this;
    }

    public SimulationBuilder withCarCountLowerBoundary(final int carsPerMinute) {
        this.carCountLowerBoundary = carsPerMinute;
        return this;
    }

    public SimulationBuilder withCarCountHigherBoundary(final int carsPerMinute) {
        this.carCountHigherBoundary = carsPerMinute;
        return this;
    }

    public SimulationBuilder withSimulationDuration(final int minutes) {
        this.simulationDuration = minutes;
        return this;
    }

    public Simulation build() {
        final SimulationTrafficEventGenerator trafficEventGenerator = new SimulationTrafficEventGenerator(
                northSouthRoadTrafficLightState,
                trafficLightsLowerBoundary,
                trafficLightsHigherBoundary,
                trafficLightSafetyMultiplier,
                simulationDuration
        );

        final SimulationCarEventGenerator simulationCarEventGenerator = new SimulationCarEventGenerator(
                carSpeedLowerBoundary,
                carSpeedHigherBoundary,
                carCountLowerBoundary,
                carCountHigherBoundary,
                simulationDuration
        );

        return new Simulation(
                trafficEventGenerator,
                simulationCarEventGenerator
        );
    }

    public static SimulationBuilder builder() {
        return new SimulationBuilder();
    }
}
