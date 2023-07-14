package org.davidlapes.crossroad;

import org.davidlapes.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CrossroadTest {

    @Test
    public void allRoadsSetUpTest() {
        final Crossroad crossroad = Data.getCrossroad();

        assertNotNull(crossroad.getRoadByOrientation(RoadOrientation.NORTH));
        assertNotNull(crossroad.getRoadByOrientation(RoadOrientation.SOUTH));
        assertNotNull(crossroad.getRoadByOrientation(RoadOrientation.WEST));
        assertNotNull(crossroad.getRoadByOrientation(RoadOrientation.EAST));
    }

    @Test
    public void allTrafficLightsSetUpTest() {
        final Crossroad crossroad = Data.getCrossroad();

        assertEquals(
                crossroad.getRoadByOrientation(RoadOrientation.NORTH).getTrafficLight().getLightName(),
                crossroad.getRoadByOrientation(RoadOrientation.SOUTH).getTrafficLight().getLightName()
        );
        assertEquals(
                crossroad.getRoadByOrientation(RoadOrientation.WEST).getTrafficLight().getLightName(),
                crossroad.getRoadByOrientation(RoadOrientation.EAST).getTrafficLight().getLightName()
        );
    }
}
