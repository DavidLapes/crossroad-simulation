package org.davidlapes.crossroad.resolver;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.Data;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.event.SwapLightsEvent;
import org.davidlapes.crossroad.event.resolver.CrossroadEventResolver;
import org.davidlapes.crossroad.event.resolver.SimpleCrossroadEventResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleCrossroadEventResolverTest {

    private static final CrossroadEventResolver EVENT_RESOLVER = new SimpleCrossroadEventResolver();

    @Test
    public void roadResolvedCorrectlyTest() {
        final Crossroad crossroad = Data.getCrossroad();
        final SwapLightsEvent event = (SwapLightsEvent) Data.getSwapLightsEvent();

        final Road road = EVENT_RESOLVER.resolve(crossroad, event);

        assertEquals(event.getRoadOrientation().name(), road.getRoadOrientation().name());
    }
}
