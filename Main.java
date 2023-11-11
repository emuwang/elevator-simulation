/**
 * Main class that runs the simulation and reports results.
 */

import java.io.*;
import java.util.*;

/*QUESTIONS TO ASK:
print stats for both elevators combined or each elevator
if args[0] file does not exist, keep running with default.properties?
values to print for analysis? make a new class for analysis?
ok to print simulation?
valid/invalid values from properties file
*/
public class Main {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("default.properties");
        Properties p = new Properties();
        p.load(fr);

        //initialize default values
        String structures = p.getProperty("structures");
        int floors = Integer.parseInt(p.getProperty("floors"));
        float passengers = Float.parseFloat(p.getProperty("passengers"));
        int elevators = Integer.parseInt(p.getProperty("elevators"));
        int elevatorCapacity = Integer.parseInt(p.getProperty("elevatorCapacity"));
        int duration = Integer.parseInt(p.getProperty("duration"));

        if (args.length > 0) { //change default values if needed
            try {
                fr = new FileReader(args[0]);
            } catch (FileNotFoundException e) {
                System.out.println("Requested properties file not found, proceeding simulation with default values.");
            }
            p = new Properties();
            p.load(fr);
            if (p.getProperty("structures") != null)
                structures = p.getProperty("structures");
            if (p.getProperty("floors") != null)
                floors = Integer.parseInt(p.getProperty("floors"));
            if (p.getProperty("passengers") != null)
                passengers = Float.parseFloat(p.getProperty("passengers"));
            if (p.getProperty("elevators") != null)
                elevators = Integer.parseInt(p.getProperty("elevators"));
            if (p.getProperty("elevatorCapacity") != null)
                elevatorCapacity = Integer.parseInt(p.getProperty("elevatorCapacity"));
            if (p.getProperty("duration") != null)
                duration = Integer.parseInt(p.getProperty("duration"));
        }

        //error handling on properties values
        boolean valid = true;
        if (structures.compareTo("array") != 0 && structures.compareTo("linked") != 0) {
            System.out.println("Invalid structure in properties file.");
            valid = false;
        }
        if (floors < 2) {
            System.out.println("Invalid number of floors in properties file.");
            valid = false;
        }
        if (passengers <= 0) {
            System.out.println("Invalid passenger chance in properties file.");
            valid = false;
        }
        if (elevators <= 0) {
            System.out.println("Invalid number of elevators in properties file.");
            valid = false;
        }
        if (elevatorCapacity <= 0) {
            System.out.println("Invalid elevator capacity in properties file.");
            valid = false;
        }
        if (duration <= 0) {
            System.out.println("Invalid duration in properties file.");
            valid = false;
        }
        if (!valid) {
            System.out.println("One or more invalid values in properties file. Exiting program.");
            return;
        }

        //Initialize floor and elevator arrays to be used in simulation
        Floor[] floorArr = new Floor[floors];
        Elevator[] elevatorArr = new Elevator[elevators];
        for (int i = 0; i < floors; i++) {
            floorArr[i] = new Floor(i, floors, passengers, structures);
        }
        for (int i = 0; i < elevators; i++) {
            elevatorArr[i] = new Elevator(elevatorCapacity, floors, floorArr);
        }

        //elevator simulation
        for (int tick = 0; tick < duration; tick++) {
            System.out.println("TICK " + tick);
            for(int floorIndex = 0; floorIndex < floors; floorIndex++) {
                floorArr[floorIndex].updatePerTick();
            }
            for (int elevatorIndex = 0; elevatorIndex < elevators; elevatorIndex++) {
                System.out.println("---ELEVATOR " + (elevatorIndex+1) + "---");
                elevatorArr[elevatorIndex].updatePerTick();
                System.out.println();
            }
            System.out.println("-----");
        }

        //print results of the simulation
        System.out.println("RESULTS OF THE SIMULATION:");
        int total = 0;
        for (int floor = 0; floor < floors; floor++) {
            total = total + floorArr[floor].getTotalPassengers();
            floorArr[floor] = null; //dereference
        }
        for (int elevator = 0; elevator < elevators; elevator++) {
            System.out.println("---ELEVATOR " + (elevator+1) + "---");
            System.out.println("Total passengers: " + total);
            elevatorArr[elevator].printTimes();
            elevatorArr[elevator] = null; //dereference
            System.out.println();
        }
    }
}
