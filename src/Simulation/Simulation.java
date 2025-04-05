package Simulation;

import Algorithms.*;
import Strategies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private static final int TIME_UNIT = 1;

    private final int diskSize;
    private final int numberOfRequests;
    private final int maxArrivalTime;
    private final int maxDeadlineTime;

    private List<Request> requests;

    private final Random rnd = new Random();

    private FCFS fcfs;
    private SSTF sstf;
    private SCAN scan;
    private C_SCAN cScan;

    private EDF edf;
    private FD_SCAN fdScan;

    private int time = 0;

    public Simulation(int numberOfRequests, int maxArrivalTime, int maxDeadlineTime, int diskSize) {
        this.numberOfRequests = numberOfRequests;
        this.maxArrivalTime = maxArrivalTime;
        this.diskSize = diskSize;
        this.maxDeadlineTime = maxDeadlineTime;

        requests = new ArrayList<>(numberOfRequests);
    }

    public void start(){

    }

    private void generateRequests(){
        for(int i = 1; i <= numberOfRequests; i++){

            if(generateWithDeadline()){
                requests.add(i,
                        new Request(
                                "R"+i,
                                rnd.nextInt(maxArrivalTime),
                                rnd.nextInt(diskSize),
                                rnd.nextInt(maxDeadlineTime)
                        )
                );
            }
            else{
                requests.add(i,
                        new Request(
                                "R"+i,
                                rnd.nextInt(maxArrivalTime),
                                rnd.nextInt(diskSize)
                        )
                );
            }

        }
    }

    private boolean generateWithDeadline() {
        return rnd.nextInt() % 2 == 0;
    }
}
