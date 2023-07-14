package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.Data;
import org.davidlapes.crossroad.Road;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SwapLightsEventTest {

    @Test
    public void lightsGetSwappedTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getSwapLightsEvent();
        crossroadManager.addEvent(event);

        final Road road = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation());

        final boolean isGreenBeforeEvent = road.getTrafficLight().isGreen();
        crossroadManager.processEvents();
        final boolean isGreenAfterEvent = road.getTrafficLight().isGreen();

        assertNotEquals(isGreenBeforeEvent, isGreenAfterEvent);
    }
}
