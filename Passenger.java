import java.util.Random;

public class Passenger {
    private int currFloor;
    private int destFloor;
    private int maxFloors;
    private int direction; //keeps track of passenger's desired direction; -1 for down, 1 for up, 0 for anything else

    /***
     * constructor for passenger object
     * @param currFloor passenger's current floor
     * @param maxFloors max number of floors
     */
    public Passenger(int currFloor, int maxFloors) {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
        Random r = new Random();
        destFloor = r.nextInt(maxFloors);
    }

    /***
     * @return passenger's current floor
     */
    public int getCurrFloor() {
        return this.currFloor;
    }

    /***
     * @return passenger's destination floor
     */
    public int getDestFloor() {
        return this.destFloor;
    }

    /***
     * @return direction of elevator
     */
    public int getDirection() {
        return this.direction;
    }
}
