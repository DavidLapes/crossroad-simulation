package org.davidlapes.crossroad.event;

import org.davidlapes.DataUtility;
import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.Data;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.trafficlight.TrafficLight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EnterCrossroadEventTest {

    @Test
    public void occupancyTrueAfterEnteringTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();
        crossroadManager.processNext();

        final boolean isRoadFree = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation())
                .isFree();

        assertFalse(isRoadFree);
    }

    @Test
    public void leaveCrossroadEventAddedTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();
        crossroadManager.processNext();

        assertEquals(DataUtility.getClassOfNextEvent(crossroadManager), LeaveCrossroadEvent.class);
    }

    @Test
    public void leaveHappensAccordingToSpeedTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final ArriveEvent event = (ArriveEvent) Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();
        crossroadManager.processNext();

        final long actualExecutionTime = DataUtility.getExecutionTimeOfNextEvent(crossroadManager);
        final long expectedExecutionTime = event.getExecutionTime() + DataUtility.getCarFromArriveEvent(event).getEscapeTime();

        assertEquals(expectedExecutionTime, actualExecutionTime);
    }

    @Test
    public void willNotEnterWhenRedLightTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();

        final TrafficLight trafficLight = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation()).getTrafficLight();

        if (trafficLight.isGreen()) {
            trafficLight.swapState();
        }

        crossroadManager.processNext();

        assertEquals(DataUtility.getClassOfNextEvent(crossroadManager), EnterCrossroadEvent.class);
    }

    @Test
    public void willNotEnterWhenOccupiedLightTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();

        final Road road = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation());

        road.setOccupied(true, Data.getCar());

        crossroadManager.processNext();

        assertEquals(DataUtility.getClassOfNextEvent(crossroadManager), EnterCrossroadEvent.class);
    }

    @Test
    public void rescheduledWithIncreasedExecutionTimeTest() {
        final CrossroadManager crossroadManager = Data.getCrossroadManager();

        final Event event = Data.getArriveEvent();
        crossroadManager.addEvent(event);

        crossroadManager.processNext();

        final Road road = crossroadManager
                .getCrossroad()
                .getRoadByOrientation(event.getRoadOrientation());

        road.setOccupied(true, Data.getCar());

        crossroadManager.processNext();

        final long expectedExecutionTime = event.getExecutionTime() + 10;
        final long actualExecutionTime = DataUtility.getExecutionTimeOfNextEvent(crossroadManager);

        assertEquals(expectedExecutionTime, actualExecutionTime);
    }
}
