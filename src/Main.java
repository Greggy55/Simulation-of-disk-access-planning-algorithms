import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,   // FCFS
                true,  // SSTF
                false,  // SCAN
                false,  // C-SCAN

                false,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                5,
                100,
                100,
                100,
                100,
                print
        );

        simulation.start();
    }
}