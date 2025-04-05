import Simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(
                100,
                100,
                100,
                100,
                20
        );

        simulation.start();
    }
}