/**
 * The Passenger class mainly keeps track of passenger's status and randomly decides their destination floor when
 * initialized.
 */

import java.util.*;

public class Passenger implements Comparable<Passenger> {
    private int currFloor;
    private int destFloor;
    private int duration; //wait time of passenger in ticks
    private boolean passengerGoingUp;
    private boolean passengerGoingDown;

    /**
     * Default constructor that initializes a Passenger object.
     * @param currFloor
     * @param maxFloors
     */
    public Passenger(int currFloor, int maxFloors) {
        this.currFloor = currFloor;
        this.duration = 0;
        this.passengerGoingUp = false;
        this.passengerGoingDown = false;
        Random rand = new Random();
        do {
            this.destFloor = rand.nextInt(maxFloors);
        } while (currFloor == this.destFloor);
        if ((destFloor - currFloor) < 0)
            this.passengerGoingDown = true;
        else
            this.passengerGoingUp = true;
    }

    /**
     * Required to make any structures involving Passenger object to sort using their destination floor.
     */
    @Override
    public int compareTo(Passenger pass) { //compare to method to sort passengers by their dest floors
        return (Integer.compare(this.destFloor, pass.destFloor));
    }

    /**
     * Increment passenger's wait time every tick.
     */
    public void addDuration() {
        duration++;
    }

    /**
     * @return the current floor passenger is on.
     */
    public int getCurrFloor() {
        return this.currFloor;
    }

    /**
     * @return the destination floor passenger wants to travel to.
     */
    public int getDestFloor() {
        return this.destFloor;
    }

    /**
     * @return passenger's wait time.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * @return passenger's direction.
     */
    public boolean isPassengerGoingUp() {
        return passengerGoingUp;
    }

    /**
     * @return passenger's direction.
     */
    public boolean isPassengerGoingDown() {
        return passengerGoingDown;
    }

}
