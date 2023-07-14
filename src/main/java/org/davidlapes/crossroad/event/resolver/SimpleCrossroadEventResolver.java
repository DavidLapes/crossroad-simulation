package org.davidlapes.crossroad.event.resolver;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.RoadOrientation;
import org.davidlapes.crossroad.event.Event;

public class SimpleCrossroadEventResolver implements CrossroadEventResolver{

    @Override
    public Road resolve(final Crossroad crossroad, final Event event) {
        final RoadOrientation roadOrientation = event.getRoadOrientation();
        return crossroad.getRoadByOrientation(roadOrientation);
    }
}
