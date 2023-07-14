package org.davidlapes.crossroad.event;

import org.davidlapes.DataUtility;
import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.Data;
import org.davidlapes.crossroad.Road;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArriveEventTest {

    @Test
    public void carAddedToQueueTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        final Road road = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation());

        final int carCountBeforeEvent = road.carCount();
        crossroadManager.processNext();
        final int carCountAfterEvent = road.carCount();

        assertEquals(carCountBeforeEvent + 1, carCountAfterEvent);
    }

    @Test
    public void enterCrossroadEventAddedTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        final int eventCountBeforeProcessing = DataUtility.getEventCount(crossroadManager);
        crossroadManager.processNext();
        final int eventCountAfterProcessing = DataUtility.getEventCount(crossroadManager);

        assertEquals(eventCountBeforeProcessing, eventCountAfterProcessing);
        assertEquals(DataUtility.getClassOfNextEvent(crossroadManager), EnterCrossroadEvent.class);
    }
}
