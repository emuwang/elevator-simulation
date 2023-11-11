/**
 * The Elevator class mainly runs the simulation using updatePerTick() method (called in Main.java), and keeps track of
 * the statistics of passenger time to report at the end of simulation.
 */

import java.util.*;

public class Elevator {
    private int currFloor;
    private int destFloor;
    private int maxFloors;
    private int maxPassengers;
    private Floor[] floorArr; //the "building" array that houses Floor objects
    private PriorityQueue<Passenger> pQueue; //passenger queue that houses the current passengers on board
    private boolean elevatorGoingUp;
    private boolean elevatorGoingDown;
    private float maxDuration = -1; //longest time for a passenger to reach dest floor; initialized at lowest int
    private float minDuration = 9999; //shortest time for a passenger to reach dest floor; initialized at a high number
    private float totalDuration = 0; //total time taken for all passengers combined
    private int passengersHandled = 0;

    /**
     * Default constructor that initializes an Elevator object.
     * @param maxPassengers
     * @param maxFloors
     * @param floorArr
     */
    Elevator(int maxPassengers, int maxFloors, Floor[] floorArr) {
        this.currFloor = 0; //default floor
        this.destFloor = 0; //default destination
        this.maxFloors = maxFloors;
        this.maxPassengers = maxPassengers;
        this.floorArr = floorArr;
        this.elevatorGoingUp = true;
        this.elevatorGoingDown = false;
        pQueue = new PriorityQueue<>();
    }

    /**
     * Called in main to update elevator related functions every tick.
     */
    public void updatePerTick() {
        updateElevator();
        moveElevator();
        updateDirection();
    }

    /**
     * Determines the floor elevator should travel to. Finds passenger that is closest to current floor Elevator is at
     * and updates elevator destination to that passenger's floor to pick them up.
     */
    private void findDestFloor() {
        Passenger closestUp = null;
        Passenger closestDown = null;
        int floorsChecked = 1;
        while ((currFloor + floorsChecked) < maxFloors) { //check upper floors
            if (floorArr[currFloor + floorsChecked].getUpQueue().size() > 0) {
                closestUp = floorArr[currFloor + floorsChecked].getUpQueue().peek();
                break;
            }
            floorsChecked++;
        }
        floorsChecked = 1;
        while ((currFloor - floorsChecked) > 0) { //check lower floors
            if (floorArr[currFloor - floorsChecked].getDownQueue().size() > 0) {
                closestDown = floorArr[currFloor - floorsChecked].getDownQueue().peek();
                break;
            }
            floorsChecked++;
        }
        if (closestUp != null && closestDown != null) { //passenger exists on both sides
            if (closestUp.getDuration() > closestDown.getDuration()) { //if passenger above has been waiting longer
                destFloor = closestUp.getCurrFloor();
            } else { //if passenger below has been waiting longer
                destFloor = closestDown.getCurrFloor();
            }
        } else if (closestUp != null) { //passenger only exist on upper floor
            destFloor = closestUp.getCurrFloor();
        } else if (closestDown != null) { //passenger only exist on lower floor
            destFloor = closestDown.getCurrFloor();
        } else { //invalid dest
            destFloor = -1;
        }
    }

    /**
     * Updates elevator every tick. Checks if dest floor needs to change, pQueue needs to change into a maxHeap or
     * minHeap, if there are any passengers to load on current floor.
     */
    private void updateElevator() {
        if(pQueue.size() > 0) { //if current queue has people in it, update dest to serve first passenger in line
            destFloor = pQueue.peek().getDestFloor();
            updateDirection();
        } else {
            if (floorArr[currFloor].getUpQueue().size() == 0 && floorArr[currFloor].getDownQueue().size() == 0) { //if no one waiting on current floor
                findDestFloor();
                updateDirection();
            } else if (floorArr[currFloor].getUpQueue().size() > 0 && floorArr[currFloor].getDownQueue().size() > 0) { //if passengers want to go both directions
                if (floorArr[currFloor].getUpQueue().peek().getDuration() < floorArr[currFloor].getDownQueue().peek().getDuration())
                    destFloor = floorArr[currFloor].getDownQueue().peek().getDestFloor();
                else
                    destFloor = floorArr[currFloor].getUpQueue().peek().getDestFloor();
            } else if (floorArr[currFloor].getUpQueue().size() > 0) { //if passengers only want to go up
                destFloor = floorArr[currFloor].getUpQueue().peek().getDestFloor();
            } else if (floorArr[currFloor].getDownQueue().size() > 0) { //if passengers only want to go down
                destFloor = floorArr[currFloor].getDownQueue().peek().getDestFloor();
            }
            updateDirection();
            if (elevatorGoingUp) //change to a min heap to find the nearest floor above
                pQueue = new PriorityQueue<>();
            if (elevatorGoingDown) //change to a max heap to find the nearest floor below
                pQueue = new PriorityQueue<>(Collections.reverseOrder());
        }
        loadPassengers();
    }

    /**
     * Move elevator to destination floor, picking and dropping off passengers as needed.
     */
    private void moveElevator() {
        int floorsMoved = 0; //limiter to keep elevator from moving more than 5 floors per tick
        if (elevatorGoingUp) { //keep moving up until dest floor is reached
            while (floorsMoved < 5 && (currFloor + 1) < maxFloors && currFloor <= destFloor) {
                System.out.println("Elevator going up from floor " + currFloor + " with " + pQueue.size() + " passengers inside.");
                currFloor++; //move up
                floorsMoved++;
                unloadPassengers();
                if (floorArr[currFloor].getUpQueue().size() > 0) { //passenger waiting on curr floor's upQueue
                    updateElevator();
                }
            }
        }
        if (elevatorGoingDown) { //keep moving down until dest floor is reached
            while (floorsMoved < 5 && (currFloor - 1) >= 0 && currFloor >= destFloor) {
                System.out.println("Elevator going down from floor " + currFloor + " with " + pQueue.size() + " passengers inside.");
                currFloor--; //move down
                floorsMoved++;
                unloadPassengers();
                if (floorArr[currFloor].getDownQueue().size() > 0) { //passenger waiting on curr floor's downQueue
                    updateElevator();
                }
            }
        }
    }

    /**
     * Update direction according to destination floor.
     */
    private void updateDirection() {
        if (destFloor - currFloor > 0) {
            elevatorGoingUp = true;
            elevatorGoingDown = false;
        } else {
            elevatorGoingUp = false;
            elevatorGoingDown = true;
        }
    }

    /**
     * Check if there are passengers that need to get off at current floor and handle passengers accordingly.
     */
    private void unloadPassengers() {
        while (pQueue.peek() != null && pQueue.peek().getDestFloor() == currFloor) { //loop until no more passengers that need to get off at curr floor
            Passenger p = pQueue.poll();
            System.out.println("Passenger(Dest: " + p.getDestFloor() + ") got off at " + currFloor + " with a duration of " + p.getDuration());
            updateTotalDuration(p);
            p = null; //dereference passenger object
        }
    }

    /**
     * Check if there are passengers that can get on at current floor and handle passengers accordingly.
     */
    private void loadPassengers() {
        if (elevatorGoingUp) {
            while(floorArr[currFloor].getUpQueue().size() > 0 && pQueue.size() < maxPassengers) {
                System.out.println("Passenger(Dest: " + floorArr[currFloor].getUpQueue().peek().getDestFloor() + ") got on at floor " + currFloor);
                pQueue.add(floorArr[currFloor].getUpQueue().poll());
            }
        }
        if (elevatorGoingDown) {
            while(floorArr[currFloor].getDownQueue().size() > 0 && pQueue.size() < maxPassengers) {
                System.out.println("Passenger(Dest: " + floorArr[currFloor].getDownQueue().peek().getDestFloor() + ") got on at floor " + currFloor);
                pQueue.add(floorArr[currFloor].getDownQueue().poll());
            }
        }
    }

    /**
     * Used in stat analysis. Update duration of passenger that just got off.
     * @param p
     */
    public void updateTotalDuration(Passenger p) {
        passengersHandled++;
        if (p.getDuration() > maxDuration)
            maxDuration = p.getDuration();
        if (p.getDuration() < minDuration)
            minDuration = p.getDuration();
        totalDuration = totalDuration + p.getDuration();
    }

    /**
     * Used in stat analysis. Print stats of the simulation for this elevator.
     */
    public void printTimes() {
        System.out.println("Passengers handled by this Elevator: " + passengersHandled);
        System.out.printf("Average time: %.2f\n", (totalDuration/passengersHandled));
        System.out.println("Shortest time: " + minDuration);
        System.out.println("Longest time: " + maxDuration);
    }
}
