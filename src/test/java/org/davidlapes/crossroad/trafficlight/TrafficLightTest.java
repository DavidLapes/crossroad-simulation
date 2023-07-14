package org.davidlapes.crossroad.trafficlight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightTest {

    @Test
    public void greenSwapsToRedTest() {
        final TrafficLight trafficLight = new TrafficLight(TrafficLightState.GREEN);
        trafficLight.swapState();

        assertEquals(trafficLight.getLightName(), TrafficLightState.RED.name());
    }

    @Test
    public void redSwapsToGreenTest() {
        final TrafficLight trafficLight = new TrafficLight(TrafficLightState.RED);
        trafficLight.swapState();

        assertEquals(trafficLight.getLightName(), TrafficLightState.GREEN.name());
    }

    @Test
    public void isGreenWhenGreenTest() {
        final TrafficLight trafficLight = new TrafficLight(TrafficLightState.GREEN);
        assertTrue(trafficLight.isGreen());
    }

    @Test
    public void isGreenWhenRedTest() {
        final TrafficLight trafficLight = new TrafficLight(TrafficLightState.RED);
        assertFalse(trafficLight.isGreen());
    }
}
