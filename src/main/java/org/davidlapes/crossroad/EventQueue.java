package org.davidlapes.crossroad;

import org.davidlapes.crossroad.event.Event;

import java.util.PriorityQueue;

public class EventQueue {

    private final PriorityQueue<Event> queue = new PriorityQueue<>();

    public void enqueue(final Event event) {
        queue.add(event);
    }

    public boolean hasEvents() {
        return !queue.isEmpty();
    }

    public int eventCount() {
        return queue.size();
    }

    public Event getNext() {
        if (queue.peek() == null) {
            throw new RuntimeException("There is no event left in event queue");
        }

        return queue.poll();
    }
}
