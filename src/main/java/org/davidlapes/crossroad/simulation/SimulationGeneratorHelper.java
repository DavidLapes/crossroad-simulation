package org.davidlapes.crossroad.simulation;

public class SimulationGeneratorHelper {

    public static int generateRandomExecutionTime(final int currentMinute) {
        return (int) (Math.random() * 60 * currentMinute);
    }

    public static int generateBoundaryNumber(
            final int lowerBoundary,
            final int higherBoundary
    ) {
        return (int) Math.round(Math.random() * (higherBoundary - lowerBoundary) + lowerBoundary);
    }
}
