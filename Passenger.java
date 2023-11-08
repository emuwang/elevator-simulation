import java.util.Random;

public class Passenger {
    private int currFloor;
    private int maxFloors;
    private int destFloor;
    private int duration;
    private int direction; //- for down, + for up, 0 if otherwise

    public Passenger(int currFloor, int maxFloors) {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
        this.duration = 0;
        Random rand = new Random();
        do {
            this.destFloor = rand.nextInt(maxFloors) + 1;
        } while (currFloor == this.destFloor);
        this.direction = destFloor - currFloor;
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

    public int getDirection() {
        return this.direction;
    }
}
