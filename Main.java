import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
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

        //change default values if needed
        //ERROR HANDLING FOR MISSING FILE??
        if (args.length > 0) {
            fr = new FileReader(args[0]);
            p = new Properties();
            p.load(fr);

            if (p.getProperty("structures") != null)
                structures = p.getProperty("structures");
            if (p.getProperty("floors") != null)
                floors = Integer.parseInt(p.getProperty("floors"));
            if (p.getProperty("passengers") != null)
                passengers = Integer.parseInt(p.getProperty("passengers"));
            if (p.getProperty("elevators") != null)
                elevators = Integer.parseInt(p.getProperty("elevators"));
            if (p.getProperty("elevatorCapacity") != null)
                elevatorCapacity = Integer.parseInt(p.getProperty("elevatorCapacity"));
            if (p.getProperty("duration") != null)
                duration = Integer.parseInt(p.getProperty("duration"));
        }

        //DEBUGGING PURPOSES TO SEE IF IT IS INITIALIZED CORRECTLY
        System.out.println(structures + ", floors: " + floors + ", passenger chance: " + passengers + ", elevators: " + elevators + ", elevator capacity: " + elevatorCapacity + ", duration: " + duration);

        Floor[] floorArr = new Floor[floors];
        Elevator[] elevatorArr = new Elevator[elevators];
        try {
            for (int i = 0; i < floors; i++) {
                floorArr[i] = new Floor(i, floors, passengers, structures);
            }
            for (int i = 0; i < elevators; i++) {
                elevatorArr[i] = new Elevator(elevatorCapacity, floors, structures, floorArr);
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        //DEBUGGING
        System.out.println("floorArr and elevatorArr declared successfully");

        //elevator simulation
        for (int tick = 0; tick < duration; tick++) {
            System.out.println("TICK " + tick);
            for(int floorIndex = 0; floorIndex < floors; floorIndex++) {
                floorArr[floorIndex].updatePerTick();
            }
            for (int elevatorIndex = 0; elevatorIndex < elevators; elevatorIndex++) {
                elevatorArr[elevatorIndex].updatePerTick();
            }
            System.out.println("-----");
        }

        for (int floor = 0; floor < floors; floor++) {
            System.out.println("--FLOOR " + floor + "--");
            floorArr[floor].printQueues();
            System.out.println();
        }
        for (int elevator = 0; elevator < elevators; elevator++) {
            System.out.println("==ELEVATOR " + elevator + "==");
            elevatorArr[elevator].printTimes();
            System.out.println();
        }
    }
}
