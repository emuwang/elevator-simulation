import java.util.*;

public class Floor{
    private int currFloor;
    private int maxFloors;
    private String structures;
    private float passengerChance;

    private Queue<Passenger> upQueue; //queue of passengers going up from the floor
    private Queue<Passenger> downQueue; //queue of passengers going down from the floor
    private Boolean headingUp;
    private Boolean headingDown;

    public Floor(int currFloor, int maxFloors, float passengerChance, String structures) throws Exception {
        this.currFloor = currFloor;
        this.maxFloors = maxFloors;
        this.passengerChance = passengerChance;
        this.structures = structures;
        this.headingUp = false;
        this.headingDown = false;
        if (structures.equals("linked")) {
            upQueue = new LinkedList<>();
            downQueue = new LinkedList<>();
        } else if (structures.equals("array")) {
            upQueue = new ArrayDeque<>();
            downQueue = new ArrayDeque<>();
        } else {
            System.out.println("structures: " + structures);
            throw new Exception("Invalid structure in Floor");
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
            if(p.getDirection() < 0)
                upQueue.add(p);
            else if(p.getDirection() > 0)
                downQueue.add(p);
        }
    }

    public Queue<Passenger> getUpQueue() {
        return upQueue;
    }

    public Queue<Passenger> getDownQueue() {
        return downQueue;
    }

    public void setHeadingUp(Boolean headingUp) {
        this.headingUp = headingUp;
    }

    public void setHeadingDown(Boolean headingDown) {
        this.headingDown = headingDown;
    }
}
