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
                5,
                100,
                100,
                10,
                100,
                print
        );

        simulation.start();
    }
}