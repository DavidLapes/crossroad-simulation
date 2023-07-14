package org.davidlapes.crossroad.simulation;

import org.davidlapes.crossroad.event.Event;

import java.util.List;

public interface SimulationEventGenerator {
    List<Event> generateEvents();
}
