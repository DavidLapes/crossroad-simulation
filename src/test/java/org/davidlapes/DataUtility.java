package org.davidlapes;

import org.davidlapes.crossroad.Car;
import org.davidlapes.crossroad.CrossroadManager;
import org.davidlapes.crossroad.EventQueue;
import org.davidlapes.crossroad.event.ArriveEvent;
import org.davidlapes.crossroad.event.Event;

import java.lang.reflect.Field;
import java.util.PriorityQueue;

public class DataUtility {

    public static long getExecutionTimeOfNextEvent(final CrossroadManager crossroadManager) {
        final Field field;

        try {
            field = crossroadManager.getClass().getDeclaredField("eventQueue");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(true);

        final EventQueue eventQueue;

        try {
            eventQueue = (EventQueue) field.get(crossroadManager);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        final Field queueField;

        try {
            queueField = eventQueue.getClass().getDeclaredField("queue");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        queueField.setAccessible(true);

        final PriorityQueue<Event> priorityQueue;

        try {
            priorityQueue = (PriorityQueue<Event>) queueField.get(eventQueue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        final Event event = priorityQueue.peek();

        return event.getExecutionTime();
    }

    public static Car getCarFromArriveEvent(final ArriveEvent event) {
        final Field field;

        try {
            field = event.getClass().getDeclaredField("car");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(true);

        final Car car;

        try {
            car = (Car) field.get(event);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return car;
    }

    public static int getEventCount(final CrossroadManager crossroadManager) {
        final Field field;

        try {
            field = crossroadManager.getClass().getDeclaredField("eventQueue");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(true);

        final EventQueue eventQueue;

        try {
            eventQueue = (EventQueue) field.get(crossroadManager);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return eventQueue.eventCount();
    }

    public static Class<? extends Event> getClassOfNextEvent(final CrossroadManager crossroadManager) {
        final Field field;

        try {
            field = crossroadManager.getClass().getDeclaredField("eventQueue");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(true);

        final EventQueue eventQueue;

        try {
            eventQueue = (EventQueue) field.get(crossroadManager);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return eventQueue.getNext().getClass();
    }
}
