import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,   // FCFS
                false,  // SSTF
                false,  // SCAN
                false,  // C-SCAN

                true,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                100,
                100,
                100,
                100,
                0,
                print
        );

        simulation.start();
    }
}