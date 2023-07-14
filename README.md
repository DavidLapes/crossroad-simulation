Crossroad Assignment
====================

Create and annotate the design of classes and interfaces for the traffic intersection simulator application. 
It should be a simple, small-scale application that could be implemented in 1-2 days of work.
The simulator contains only one specific intersection. 
It is a classic intersection of 4 roads (North-South-East-West),
each with only one simple traffic light in the incoming direction, showing only green or red.
The traffic lights are controlled so that for some time t1 the traffic lights on the north and south roads are green,
while the west and east roads are red. Subsequently, the colors are swapped, 
and they glow in the opposite direction for a period of t2. 
And so on and on. 

Cars arrive at the intersection from all 4 directions in numbers: 
from the north c1 cars per minute, from the south c2 cars per minute, 
from the west c3 cars per minute, from the east c4 cars per minute. 
Cars leave the intersection in the same order in which they arrived. 
It takes a car s1 second to leave the intersection before the next car can leave. 
All cars only go straight, none turn. No other circumstances or elements play a role in the simulation. 
So don't deal with, for example, car collisions, right-of-way, etc.

Here is an example of how to grasp the problem: 
better than a real-time simulation, 
the application can be designed as a discrete simulation.
This means that the application will have a sort of time queue of events.
In it, scheduled events will be sorted according to the time they are supposed to occur.
Then a loop runs in the application that sequentially selects
the event queue and fires those events.

An example of an event could be:

- a new car arrived at the intersection from the east
- traffic lights switch lights


Handling such an event can result in the modification of various objects 
in the application or, for example, adding new events to the time queue or removing them.

# Prerequisites
- Java 20 (can be downgraded down to 14 since I used switch-case feature that was added in Java 14)
- Maven (should be fine with anything not too old)

# Design

Crossroad simulation is written using discrete simulation
that is driven by events flowing through priority queues.
By design, every crossroad has its own priority queue, or in code `CrossroadManager`
that contains both `Crossroad` and `EventQueue`.
Currently, there is only support for crossroad that has:

- 4 roads (every road only goes straight)
- 4 traffic lights (1 per road)

Design of the code should allow inheritance, thus possibly allowing
more complex crossroads with rules. There would need to be some change done,
like for example moving some logic from events to other classes.
Right now, it is usually `Road` that is responsible for executing events, so
there would have to be a need for another manager that would extract the tight relationships
and would be responsible for all the traffic logic.
We are using `RoadOrientation` with values 
`NORTH`, `SOUTH`, `WEST`, `EAST` to distinguish between roads.


In terms of intersection blocking to avoid crashes, right now there is possibility
of crashing into another car from neighbouring road if a `Car A` enters crossroad
with a minimal speed, then neighbouring road traffic light turns `GREEN` and a `Car B`
rushes into the intersection with much greater speed, then the crash will definitely happen.
However, in this simulation we don't track crashes. 

We do take care of only one type
of crashes and that is car crashing from back. If a car enters intersection,
another car that is waiting behind and should be served next has to wait until
the car leaves the crossroad. 
This check is not done for opposite road from perspective of the same road, 
since there is no collision possible if both cars remain in their lines, which in
our simulation they do. By this we mean that if you are about to enter the intersection,
you don't need to check if there is a car in opposite road, only in front of you.

# Classes Explained

- `CrossroadManager` takes care of holding `Crossroad`
state, `EventQueue` and being responsible for executing events
- `EventQueue` is a simple event queue that uses `java.util.PriorityQueue<Event>`
and serves events from the queue, but does not execute them
- `Crossroad` consists of 4 roads (1 for every point of the compass)
- `Road` has `RoadOrientation` enum value, `Queue<Car>` car queue, 
- `CrossroadBuilder` is inside `Crossroad.java` that helps build
  the `Crossroad` by accepting 2 traffic light states (1 for `NORTH`/`SOUTH`, 1 for `WEST`/`EAST`),
  `TrafficLight` with current light, `Car` for which (if) car is in intersection from that road,
and `boolean isOccupied` to say if the road is occupied or not. This class is also responsible for
providing functions to run for all events
- `Car` is a pure entity class that is self-explanatory with time in seconds of
how fast it escapes intersection once it gets there
- `TrafficLight` holds enum value `TrafficLightState` which has either `RED` or `GREEN` color
- `EventResult` has a result of event of either `SUCCESS` or `FAILED` and `eventToEnqueue`
in case an event fails and needs to be requeued (for example there is a `RED` light)
or it chains to another event
- `Event` is an interface that consists of `processEvent(Crossroad)` that
takes `Crossroad` as a parameter, because it makes sense to have events that can happen
regardless of a crossroad, but every crossroad can handle it differently,
this gets injected by the `CrossroadManager` when we add the `Event` to the `EventQueue`
- `ArriveEvent` basically says that we have a `Car` that comes to road from specific
`RoadOrientation` at specific `executionTime`. It chains into another event `EnterCrossroadEvent`
- `EnterCrossroadEvent` is chained from `ArriveEvent` and does not need any parameter, just a
`RoadOrientation`, because our ways of having a car come at specific time and being
taken out of car queue happens in a FIFO way, so we don't have to search for any ID, we just
  `poll()` the car. It checks if the road is not occupied and if there is `GREEN` light
- `LeaveCrossroadEvent` is chained from `EnterCrossroadEvent` and all it does is
mark the road empty after adding `escapeTime` from `Car` to the `executionTime` that was provided
from `EnterCrossroadEvent` the moment it processed as `SUCCESS`
- `SwapLightsEvent` is an isolated event that does not chain from anything and does not chain any other event,
all it does is change light for a given road (thanks to this we can change all lights asynchronously)
- `CrossroadEventResolver` is an interface that gives us the option to get `Road` object
by providing `Event` and `Crossroad`
- `SimpleCrossroadEventResolver` is an implementation of `CrossroadEventResolver` that resolves
`Road` simply by `RoadOrientation` taken from `Event`

# Running Simulation

Inside `main` function in `AssignmentBoot` class there is a default simulation built that
can be run. It currently randomizes these steps:

- Random time between every traffic light swap (all 4 lights swap at the same time)
- Random car count for every minute for every road
- Random car speed for every car (in seconds)

# Configuring Simulation

- `northSouthRoadTrafficLightState` is to determine starting lights for `NORTH`/`SOUTH` and `WEST`/`EAST`
- `trafficLightsLowerBoundary` and `trafficLightsHigherBoundary` are to set number range
from which there is randomly generated cycles of swaps
- `trafficLightSafetyMultiplier` is to prevent the situation where cars take too long to
cross the crossroad and there is not enough traffic light events to handle it, so
multiplier basically multiplies number of traffic light events
- `carSpeedLowerBoundary` and `carSpeedHigherBoundary` are to set number range
from which there is randomly generated time in seconds that cars take to leave intersection
- `carCountLowerBoundary` and `carCountHigherBoundary` are to set number range
from which there is randomly generated count of arriving cars for every road for every minute
- `simulationDuration` duration in minutes that is filled with events, but does not realistically
mean the duration will be either less, equal or more than the one provided, just how many
events we generate, since after all this is a discrete simulation
