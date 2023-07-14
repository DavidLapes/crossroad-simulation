package org.davidlapes.crossroad;

import java.util.UUID;

public class Car {

    private final UUID uuid;
    private final int escapeTime;

    public Car(final int escapeTime) {
        if (escapeTime < 0) {
            throw new IllegalArgumentException("Escape time must not be negative");
        }

        this.uuid = UUID.randomUUID();
        this.escapeTime = escapeTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUuidAsString() {
        return getUuid().toString();
    }

    public long getEscapeTime() {
        return escapeTime;
    }
}
