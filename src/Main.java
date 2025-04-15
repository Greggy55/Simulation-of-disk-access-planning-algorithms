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

        int numberOfRequests = 1000;
        int maxArrivalTime = 500;
        int diskSize = 100;

        int maxDeadlineTime = 100;
        int percentOfProcessesWithDeadline = 0;

        int numberOfBehindHeadRequests = 0;

        int numberOfCloseTogetherRequests = 0; // 200
        int centerOfCloseTogetherRequests = 10;
        int radiusOfCloseTogetherRequests = 5;

        Simulation simulation = new Simulation(
                numberOfRequests,
                maxArrivalTime,
                diskSize,
                maxDeadlineTime,
                percentOfProcessesWithDeadline,
                numberOfBehindHeadRequests,
                numberOfCloseTogetherRequests,
                centerOfCloseTogetherRequests,
                radiusOfCloseTogetherRequests,
                print
        );

        simulation.start();
    }
}