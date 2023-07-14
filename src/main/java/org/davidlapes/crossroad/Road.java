package org.davidlapes.crossroad;

import org.davidlapes.crossroad.trafficlight.TrafficLight;

import java.util.LinkedList;
import java.util.Queue;

public class Road {

    private final RoadOrientation orientation;
    private final Queue<Car> cars;
    private final TrafficLight trafficLight;
    private boolean isOccupied;
    private Car occupyingCar;

    public Road(final RoadOrientation orientation, final TrafficLight trafficLight) {
        if (trafficLight == null) {
            throw new IllegalArgumentException("Provided TrafficLight can not be null");
        }

        this.orientation = orientation;
        this.cars = new LinkedList<>();
        this.trafficLight = trafficLight;
        this.isOccupied = false;
    }

    public void processArrive(final Car car) {
        cars.add(car);
        System.out.println("Car " + car.getUuidAsString() + " is arriving to road " + this.orientation + ".");
    }

    public Car processEnterCrossroad() {
        if (cars.peek() == null) {
            throw new RuntimeException("There is no car left on given road.");
        }

        final Car car = cars.poll();
        setOccupied(true, car);

        System.out.println("Car " + occupyingCar.getUuidAsString() + " has entered crossroad from road " + orientation.name() + ".");

        return occupyingCar;
    }

    public void processLeaveCrossroad() {
        System.out.println("Car " + occupyingCar.getUuidAsString() + " has left crossroad.");
        setOccupied(false, null);
    }

    public void processLightSwitch() {
        trafficLight.swapState();
        System.out.println("Swapping " + orientation.name() + " traffic lights to " + trafficLight.getLightName() + ".");
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public boolean isFree() {
        return !isOccupied;
    }

    public void setOccupied(final boolean isOccupied, final Car occupyingCar) {
        if (isOccupied && occupyingCar == null) {
            throw new IllegalArgumentException("If road is to be set occupied, that car has to be specified.");
        }

        if (!isOccupied && occupyingCar != null) {
            throw new IllegalArgumentException("If road is to be set free, a car can not be there.");
        }

        this.isOccupied = isOccupied;
        this.occupyingCar = occupyingCar;
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public int carCount() {
        return cars.size();
    }

    public RoadOrientation getRoadOrientation() {
        return orientation;
    }
}
