package org.davidlapes.crossroad.event.resolver;

import org.davidlapes.crossroad.Crossroad;
import org.davidlapes.crossroad.Road;
import org.davidlapes.crossroad.event.Event;

public interface CrossroadEventResolver {
    Road resolve(Crossroad crossroad, Event event);
}
