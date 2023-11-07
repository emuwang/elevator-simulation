import java.util.*;

public class Floor{
    private Queue<Passenger> up;
    private Queue<Passenger> down;
    private int currFloor;
    private int maxFloors;
    private String structures;
    private float passengers;

    public Floor(int currFloor, int maxFloors) {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
    }
}
