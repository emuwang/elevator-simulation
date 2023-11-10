import java.util.*;

public class Elevator {
    private int currFloor;
    private int destFloor;
    private int maxFloors;
    private int maxPassengers;
    private Floor[] floorArr;
    private Queue<Passenger> pQueue;
    private boolean elevatorGoingUp;
    private boolean elevatorGoingDown;
    private float maxDuration = -1;
    private float minDuration = 100;
    private float totalDuration = 0;
    private int passengersHandled = 0;

    Elevator(int maxPassengers, int maxFloors, String structures, Floor[] floorArr) throws Exception {
        this.currFloor = 0; //default floor
        this.destFloor = 0;
        this.maxFloors = maxFloors;
        this.maxPassengers = maxPassengers;
        this.floorArr = floorArr;
        this.elevatorGoingUp = true;
        this.elevatorGoingDown = false;
        pQueue = new PriorityQueue<>();
    }

    public void updatePerTick() {
        for (Passenger p: pQueue)
            p.addDuration();
        int floorsTravelled = 0;
        if(elevatorGoingUp) {
            pQueue = new PriorityQueue<Passenger>(); //min heap if going up
            do { //check 5 floors from currFloor and above
                if (currFloor >= maxFloors) {
                    currFloor--;
                    elevatorGoingUp = false;
                    elevatorGoingDown = true;
                    break;
                }
                System.out.println("Elevator going up from floor " + currFloor + ", num of passengers on board: " + pQueue.size());
                if (currFloor >= 0 && currFloor < maxFloors && floorArr[currFloor].getUpQueue().size() > 0) {
                    while (pQueue.size() < maxPassengers && floorArr[currFloor].getUpQueue().size() > 0) {
                        Passenger p = floorArr[currFloor].getUpQueue().poll();
                        pQueue.add(p);
                        System.out.println("Passenger got on Elevator at floor " + currFloor + ", their dest is floor " + p.getDestFloor());
                    }
                }
                updateCurrFloor();
                currFloor++;
                floorsTravelled++;
            } while (floorsTravelled < 5);
        }
        if (elevatorGoingDown) {
            pQueue = new PriorityQueue<Passenger>(Collections.reverseOrder()); //max heap if going down
            do { //check 5 floors from currFloor and above
                if (currFloor < 0) {
                    currFloor++;
                    elevatorGoingUp = true;
                    elevatorGoingDown = false;
                    break;
                }
                System.out.println("Elevator going down from floor " + currFloor + ", num of passengers on board: " + pQueue.size());
                if (currFloor >= 0 && currFloor < maxFloors && floorArr[currFloor].getDownQueue().size() > 0) {
                    while (pQueue.size() < maxPassengers && floorArr[currFloor].getDownQueue().size() > 0) {
                        Passenger p = floorArr[currFloor].getDownQueue().poll();
                        pQueue.add(p);
                        System.out.println("Passenger got on Elevator at floor " + currFloor + ", their dest is floor " + p.getDestFloor());
                    }
                }
                updateCurrFloor();
                currFloor--;
                floorsTravelled++;
            } while (floorsTravelled < 5);
        }
    }

    private void updateCurrFloor() { //check if there are passengers that need to get off at curr floor and handle passengers accordingly
        while (pQueue.peek() != null && pQueue.peek().getDestFloor() == this.currFloor) {
            Passenger p = pQueue.poll();
            System.out.println("Passenger(" + p.getDestFloor() + ") in elevator got off at " + currFloor + " with a duration of " + p.getDuration());
            updateDuration(p);
        }
    }

    public void updateDuration(Passenger p) {
        passengersHandled++;
        if (p.getDuration() > maxDuration)
            maxDuration = p.getDuration();
        if (p.getDuration() < minDuration)
            minDuration = p.getDuration();
        totalDuration = totalDuration + p.getDuration();
    }

    public void printTimes() {
        System.out.println("Passengers handled: " + passengersHandled);
        System.out.printf("Average time: %.2f\n", (totalDuration/passengersHandled));
        System.out.println("Shortest time: " + minDuration);
        System.out.println("Longest time: " + maxDuration);
    }
}
