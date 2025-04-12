import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,  // FCFS
                false,  // SSTF
                false,  // SCAN
                false,  // C-SCAN

                false,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                1000,
                500,
                100,

                100,
                0,

                0,

                0,
                10,
                5,
                print
        );

        simulation.start();
    }
}