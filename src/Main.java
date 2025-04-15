import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,  // FCFS
                false,  // SSTF
                false,  // SCAN
                false,  // C-SCAN

                false,  // EDF
                true   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                1000,
                500,
                100,

                100,
                50,

                0,

                0,
                10,
                5,
                print
        );
        // NOCTR = 500

        simulation.start();
    }
}