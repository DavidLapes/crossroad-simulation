package org.davidlapes;

import org.davidlapes.crossroad.*;
import org.davidlapes.crossroad.event.ArriveEvent;
import org.davidlapes.crossroad.event.Event;
import org.davidlapes.crossroad.event.LeaveCrossroadEvent;
import org.davidlapes.crossroad.event.SwapLightsEvent;
import org.davidlapes.crossroad.trafficlight.TrafficLight;
import org.davidlapes.crossroad.trafficlight.TrafficLightState;

public class Data {

    public static CrossroadManager getCrossroadManager() {
        return new CrossroadManager(getCrossroad());
    }

    public static int getRandomExecutionTime() {
        return (int) Math.round(Math.random() * 5);
    }

    public static Event getArriveEvent() {
        return new ArriveEvent(RoadOrientation.NORTH, getRandomExecutionTime(), getCar());
    }

    public static Event getLeaveCrossroadEvent() {
        return new LeaveCrossroadEvent(RoadOrientation.NORTH, getRandomExecutionTime());
    }

    public static Event getSwapLightsEvent() {
        return getSwapLightsEvent(getRandomExecutionTime());
    }

    public static Event getSwapLightsEvent(final int executionTime) {
        return new SwapLightsEvent(RoadOrientation.NORTH, executionTime);
    }

    public static Road getRoad() {
        final TrafficLight trafficLight = new TrafficLight(TrafficLightState.GREEN);
        return new Road(RoadOrientation.NORTH, trafficLight);
    }

    public static Car getCar() {
        return new Car(getRandomExecutionTime());
    }

    public static Crossroad getCrossroad() {
        return Crossroad.CrossroadBuilder
                .getBuilder()
                .withCommonTrafficLightStates(TrafficLightState.GREEN, TrafficLightState.RED)
                .build();
    }
}
