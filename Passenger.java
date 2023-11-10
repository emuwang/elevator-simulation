import java.util.*;

public class Passenger implements Comparable<Passenger>{
    private int currFloor;
    private int destFloor;
    private int duration;
    private boolean passengerGoingUp;
    private boolean passengerGoingDown;

    public Passenger(int currFloor, int maxFloors) {
        this.currFloor = currFloor;
        this.duration = 0;
        this.passengerGoingUp = false;
        this.passengerGoingDown = false;
        Random rand = new Random();
        do {
            this.destFloor = rand.nextInt(maxFloors) + 1;
        } while (currFloor == this.destFloor);
        if ((destFloor - currFloor) < 0)
            this.passengerGoingDown = true;
        else
            this.passengerGoingUp = true;

    }

    public void addDuration() {
        duration++;
    }

    public int getCurrFloor() {
        return this.currFloor;
    }

    public int getDestFloor() {
        return this.destFloor;
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean isPassengerGoingUp() {
        return passengerGoingUp;
    }

    public boolean isPassengerGoingDown() {
        return passengerGoingDown;
    }

    @Override
    public int compareTo(Passenger o) {
        return 0;
    }
}
