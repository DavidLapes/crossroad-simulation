package org.davidlapes.crossroad;

import org.davidlapes.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoadTest {

    @Test
    public void carsUpdatedDuringArrivalTest() {
        final Road road = Data.getRoad();
        final Car car = Data.getCar();

        final int countBeforeArrive = road.carCount();
        road.processArrive(car);
        final int countAfterArrive = road.carCount();

        assertEquals(countBeforeArrive + 1, countAfterArrive);
    }

    @Test
    public void carsUpdatedDuringDepartureTest() {
        final Road road = Data.getRoad();
        final Car car = Data.getCar();

        road.processArrive(car);
        final int countBeforeDeparture = road.carCount();
        road.processEnterCrossroad();
        final int countAfterDeparture = road.carCount();

        assertEquals(countBeforeDeparture - 1, countAfterDeparture);
    }

    @Test
    public void occupancyChangedDuringDepartureTest() {
        final Road road = Data.getRoad();
        final Car car = Data.getCar();

        road.processArrive(car);
        road.processEnterCrossroad();

        assertFalse(road.isFree());
    }

    @Test
    public void occupancyChangedDuringLeavingTest() {
        final Road road = Data.getRoad();
        final Car car = Data.getCar();

        road.processArrive(car);
        road.processEnterCrossroad();
        road.processLeaveCrossroad();

        assertTrue(road.isFree());
    }
}
