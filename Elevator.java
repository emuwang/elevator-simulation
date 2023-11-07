import java.util.*;

public class Elevator {
    private int currFloor;
    private int destFloor;
    private int currPassengers;
    private int maxPassengers;
    private List<Floor> elevator;
    private int direction; //keeps track of elevator's current direction; - for down, + for up, 0 if elevator isn't moving
    private Queue<Passenger> passengerQueue;

    public Elevator(int elevatorCapacity, String structures, Floor[] elevatorContainer) throws Exception{
        this.currFloor = 0; //initialize as 1st floor
        this.currPassengers = 0;
        this.maxPassengers = elevatorCapacity;
        this.direction = 1; //initialize as up because elevator initializes at 1st floor
        this.passengerQueue = new PriorityQueue<Passenger>();
        if (structures == "linked")
            elevator = new LinkedList<>();
        else if (structures == "array")
            elevator = new ArrayList<>();
        else
            throw new Exception("Invalid Structure Type");
    }

    private int findDestFloor() {
        Passenger up = null;
        Passenger down = null;

        int distance = 1;
        while ((currFloor - distance) >= 0) {
            if(elevator.add())
        }

        return 0;
    }
}
