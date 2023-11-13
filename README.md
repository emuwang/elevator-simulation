# Elevator Simulation
Run the simulation using Main.java. Elevator.java, Floor.java and Passenger.java are used to store their respective classes.

Notes for this implementation:
- Elevators go a maximum of 5 floors per tick.
- It rejects certain values in the .properties file. The requirements for the simulation to run successfully are as follows:
   - structures: "array" or "linked".
   - floors: at least 2 for elevator(s) to travel between.
   - passengers: a float greater than 0.0 and less than 1.0 for passengers to generate so that elevators can pick them up.
   - elevators: at least 1 for the simulation to run.
   - elevatorCapacity: at least 1 so that it can transport passengers from floor to floor.
   - duration: at least 1 tick of simulation.
- If any required values are missing in the .properties file, they are replaced with values in the default.properties file. If the .properties file in args[0] is not found, the simulation will proceed with the default values.
- It prints out the entire simulation by default. Every tick, it prints the following information:
  - Tick number.
  - Any passengers generated, along with their current floor, destination floor and direction.
  - For all elevators, their movement from current floor with the number of passengers on board, as well as passengers getting on and off (with their destination and time passed since their creation).
- The report for simulation prints the total passengers generate, the number of passengers served, average time, shortest time and longest wait time.