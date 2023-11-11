/**
 * The Floor class mainly keeps track of passengers on current floor, and generate passengers according to the chance
 * given by the properties file.
 */

import java.util.*;

public class Floor{
    private int currFloor;
    private int maxFloors;
    private float passengerChance;
    private int totalPassengers = 0;
    private Queue<Passenger> upQueue; //queue of passengers going up from the floor
    private Queue<Passenger> downQueue; //queue of passengers going down from the floor

    /**
     * Default constructor
     * @param currFloor
     * @param maxFloors
     * @param passengerChance
     * @param structures
     * @throws Exception
     */
    public Floor(int currFloor, int maxFloors, float passengerChance, String structures) throws Exception {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
        this.passengerChance = passengerChance;
        if (structures.equals("linked")) {
            upQueue = new LinkedList<>();
            downQueue = new LinkedList<>();
        } else if (structures.equals("array")) {
            upQueue = new ArrayDeque<>();
            downQueue = new ArrayDeque<>();
        } else {
            System.out.println("Expected structures: linked/array. Current structures: " + structures);
            throw new Exception("Invalid structure in Floor.");
        }
    }

    public void updatePerTick() {
        for (Passenger upP: upQueue)
            upP.addDuration();
        for (Passenger downP: downQueue)
            downP.addDuration();

        Random rand = new Random();
        if(rand.nextFloat() < passengerChance) {
            Passenger p = new Passenger(currFloor, maxFloors);
            totalPassengers++;
            System.out.print("Passenger generated at floor " + currFloor + " with a dest floor of " + p.getDestFloor());
            if(p.isPassengerGoingUp()) {
                System.out.print(" and passenger is going up. Added to upQueue.");
                upQueue.add(p);
            } else if(p.isPassengerGoingDown()) {
                System.out.print(" and passenger is going down. Added to downQueue.");
                downQueue.add(p);
            }
            System.out.println();
        }
    }

    public Queue<Passenger> getUpQueue() {
        return upQueue;
    }

    public Queue<Passenger> getDownQueue() {
        return downQueue;
    }


    public int getTotalPassengers() {
        return totalPassengers;
    }
}
