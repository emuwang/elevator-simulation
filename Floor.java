/**
 * The Floor class mainly keeps track of passengers on current floor, and generate passengers according to the chance
 * given by the properties file.
 */

import java.util.*;

public class Floor{
    private int currFloor;
    private int maxFloors;
    private float passengerChance;
    private int totalPassengers = 0; //global variable to keep track of total number of passengers generated
    private Queue<Passenger> upQueue; //queue of passengers going up from the floor
    private Queue<Passenger> downQueue; //queue of passengers going down from the floor

    /**
     * Default constructor that initializes a Floor object.
     * @param currFloor
     * @param maxFloors
     * @param passengerChance
     * @param structures
     */
    public Floor(int currFloor, int maxFloors, float passengerChance, String structures) {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
        this.passengerChance = passengerChance;
        //uses polymorphism to change to an LLQueue or Deque(array based) depending on properties file
        if (structures.equalsIgnoreCase("linked")) {
            upQueue = new LinkedList<>();
            downQueue = new LinkedList<>();
        } else if (structures.equalsIgnoreCase("array")) {
            upQueue = new ArrayDeque<>();
            downQueue = new ArrayDeque<>();
        }
    }

    /**
     * Called in main to update floor related variables every tick.
     */
    public void updatePerTick() {
        //add duration to passengers
        for (Passenger upP: upQueue)
            upP.addDuration();
        for (Passenger downP: downQueue)
            downP.addDuration();
        //generate passenger floor according to value passed from properties file
        Random rand = new Random();
        if(rand.nextFloat() < passengerChance) {
            Passenger p = new Passenger(currFloor, maxFloors);
            totalPassengers++;
            System.out.print("Passenger generated at floor " + currFloor + " with a dest floor of " + p.getDestFloor());
            if(p.isPassengerGoingUp()) {
                System.out.println(" and passenger is going up. Added to upQueue.");
                upQueue.add(p);
            }
            if(p.isPassengerGoingDown()) {
                System.out.println(" and passenger is going down. Added to downQueue.");
                downQueue.add(p);
            }
        }
    }

    /**
     * @return passengers that wants to go to upper floors
     */
    public Queue<Passenger> getUpQueue() {
        return upQueue;
    }

    /**
     * @return passengers that wants to go to lower floors
     */
    public Queue<Passenger> getDownQueue() {
        return downQueue;
    }

    /**
     * @return total number of passengers generated to be used in main
     */
    public int getTotalPassengers() {
        return totalPassengers;
    }
}
