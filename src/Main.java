import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        boolean[] print = {
                true,   // FCFS
                false,  // SSTF
                false,  // SCAN
                false,  // C-SCAN

                false,  // EDF
                false   // FD-SCAN
        };

        Simulation simulation = new Simulation(
                3,
                100,
                100,
                100,
                20,
                print
        );

        simulation.start();
    }
}