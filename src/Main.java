import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                false,  // FCFS
                false,  // SSTF
                false,  // SCAN
                true,  // C-SCAN

                false,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                100,
                1000,
                100,
                100,
                20,
                print
        );

        simulation.start();
    }
}