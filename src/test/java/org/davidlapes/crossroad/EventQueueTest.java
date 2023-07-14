package org.davidlapes.crossroad;

import org.davidlapes.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventQueueTest {

    @Test
    public void correctCountOfEventsTest() {
        final EventQueue eventQueue = new EventQueue();

        final int eventCount = 10;

        for(int i = 0; i < eventCount; i++) {
            eventQueue.enqueue(Data.getSwapLightsEvent());
        }

        assertEquals(eventQueue.eventCount(), eventCount);
    }

    @Test
    public void eventGetsEnqueuedCorrectlyTest() {
        final EventQueue eventQueue = new EventQueue();

        eventQueue.enqueue(Data.getSwapLightsEvent(20));
        eventQueue.enqueue(Data.getSwapLightsEvent(10));
        eventQueue.enqueue(Data.getSwapLightsEvent(30));

        assertEquals(eventQueue.getNext().getExecutionTime(), 10);
        assertEquals(eventQueue.getNext().getExecutionTime(), 20);
        assertEquals(eventQueue.getNext().getExecutionTime(), 30);
    }
}
