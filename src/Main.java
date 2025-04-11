import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,   // FCFS
                false,  // SSTF
                true,  // SCAN
                true,  // C-SCAN

                false,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                5,
                100,
                100,
                100,
                0,
                print
        );

        simulation.start();
    }
}