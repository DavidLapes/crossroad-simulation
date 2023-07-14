package org.davidlapes.crossroad.trafficlight;

public class TrafficLight {

    private TrafficLightState trafficLightState;

    public TrafficLight(final TrafficLightState trafficLightState) {
        if(trafficLightState == null) {
            throw new IllegalArgumentException("Initial TrafficLightState can not be null");
        }

        this.trafficLightState = trafficLightState;
    }

    public void swapState() {
        if (this.trafficLightState == TrafficLightState.RED) {
            this.trafficLightState = TrafficLightState.GREEN;
        } else if (this.trafficLightState == TrafficLightState.GREEN) {
            this.trafficLightState = TrafficLightState.RED;
        } else {
            throw new RuntimeException("Traffic light is in invalid state - { " + trafficLightState + " }");
        }
    }

    public boolean isGreen() {
        return this.trafficLightState == TrafficLightState.GREEN;
    }

    public String getLightName() {
        return this.trafficLightState.name();
    }
}
