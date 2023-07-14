package org.davidlapes.crossroad;

import org.davidlapes.crossroad.trafficlight.TrafficLight;
import org.davidlapes.crossroad.trafficlight.TrafficLightState;

public class Crossroad {

    private final Road roadNorth;
    private final Road roadSouth;
    private final Road roadEast;
    private final Road roadWest;

    public Road getRoadByOrientation(final RoadOrientation roadOrientation) {
        switch (roadOrientation) {
            case NORTH -> {
                return roadNorth;
            }

            case SOUTH -> {
                return roadSouth;
            }

            case EAST -> {
                return roadEast;
            }

            case WEST -> {
                return roadWest;
            }

            default -> throw new RuntimeException(
                    "Provided road orientation has unknown orientation - {" + roadOrientation + "}"
            );
        }
    }

    private Crossroad(
            final Road roadNorth,
            final Road roadSouth,
            final Road roadWest,
            final Road roadEast
    ) {
        this.roadNorth = roadNorth;
        this.roadSouth = roadSouth;
        this.roadWest = roadWest;
        this.roadEast = roadEast;
    }

    public static class CrossroadBuilder {
        private Road roadNorth;
        private Road roadSouth;
        private Road roadEast;
        private Road roadWest;

        public CrossroadBuilder() {}

        public CrossroadBuilder withCommonTrafficLightStates(
                final TrafficLightState trafficLightStateNorthSouth,
                final TrafficLightState trafficLightStateEastWest
        ) {
            if (trafficLightStateNorthSouth == trafficLightStateEastWest) {
                throw new IllegalArgumentException("Traffic light states can not be same");
            }

            final TrafficLight trafficLightNorth = new TrafficLight(trafficLightStateNorthSouth);
            final TrafficLight trafficLightSouth = new TrafficLight(trafficLightStateNorthSouth);

            final TrafficLight trafficLightEast = new TrafficLight(trafficLightStateEastWest);
            final TrafficLight trafficLightWest = new TrafficLight(trafficLightStateEastWest);

            final Road roadNorth = new Road(RoadOrientation.NORTH, trafficLightNorth);
            final Road roadSouth = new Road(RoadOrientation.SOUTH, trafficLightSouth);
            final Road roadEast = new Road(RoadOrientation.EAST, trafficLightEast);
            final Road roadWest = new Road(RoadOrientation.WEST, trafficLightWest);

            this.roadNorth = roadNorth;
            this.roadSouth = roadSouth;
            this.roadWest = roadWest;
            this.roadEast = roadEast;

            return this;
        }

        public Crossroad build() {
            return new Crossroad(roadNorth, roadSouth, roadWest, roadEast);
        }

        public static CrossroadBuilder getBuilder() {
            return new CrossroadBuilder();
        }
    }
}
