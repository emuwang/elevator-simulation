import java.util.*;

public class Elevator {
    private int currFloor;
    private int maxFloors;
    private int maxPassengers;
    private String structures;
    private Floor[] floorArr;
    private PriorityQueue<Passenger> pQueue;
    private int direction;

    Elevator(int maxPassengers, int maxFloors, String structures, Floor[] floorArr) throws Exception {
        this.currFloor = 0; //default floor
        this.maxFloors = maxFloors;
        this.maxPassengers = maxPassengers;
        this.structures = structures;
        this.floorArr = floorArr;
        pQueue = new PriorityQueue<>();
    }
}
