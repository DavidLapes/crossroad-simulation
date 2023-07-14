package org.davidlapes.crossroad.event;

import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaveCrossroadEventTest {

    @Test
    public void occupancyFalseAfterLeavingTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getLeaveCrossroadEvent();
        crossroadManager.addEvent(event);

        crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation())
                .setOccupied(true, Data.getCar());

        crossroadManager.processEvents();

        final boolean isRoadFree = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation())
                .isFree();

        assertTrue(isRoadFree);
    }
}
