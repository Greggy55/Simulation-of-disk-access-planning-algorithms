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
    private final int percentOfProcessesWithDeadline;

    private List<Request> requests;

    private final Random rnd = new Random();

    private FCFS fcfs;
    private SSTF sstf;
    private SCAN scan;
    private C_SCAN cScan;

    private EDF edf;
    private FD_SCAN fdScan;

    private int time = 0;

    public Simulation(int numberOfRequests,
                      int maxArrivalTime,
                      int diskSize,
                      int maxDeadlineTime,
                      int percentOfProcessesWithDeadline
    ) {
        this.numberOfRequests = numberOfRequests;
        this.maxArrivalTime = maxArrivalTime;
        this.diskSize = diskSize;
        this.maxDeadlineTime = maxDeadlineTime;

        if(percentOfProcessesWithDeadline < 0){
            percentOfProcessesWithDeadline = 0;
        }
        else if(percentOfProcessesWithDeadline > 100){
            percentOfProcessesWithDeadline = 100;
        }

        this.percentOfProcessesWithDeadline = percentOfProcessesWithDeadline;

        requests = new ArrayList<>();
    }

    public void start(){
        generateRequests();
        //for(Request request : requests){System.out.println(request);}

        int requestIndex = 0;
        while(requestsExist(requestIndex)){

        }
    }

    private void addRequest(){

    }

    private void generateRequests(){
        for(int i = 0; i < numberOfRequests; i++){

            if(generateWithDeadline()){
                requests.add(
                        new Request(
                                rnd.nextInt(maxArrivalTime),
                                rnd.nextInt(diskSize),
                                rnd.nextInt(maxDeadlineTime)
                        )
                );
            }
            else{
                requests.add(
                        new Request(
                                rnd.nextInt(maxArrivalTime),
                                rnd.nextInt(diskSize)
                        )
                );
            }
        }
        // sort ascending by arrival time
        requests.sort((r1, r2) -> Integer.compare(r1.getArrivalTime(), r2.getArrivalTime()));
    }

    private boolean generateWithDeadline() {
        return rnd.nextInt() % (100/percentOfProcessesWithDeadline) == 0;
    }

    private boolean requestsExist(int requestIndex) {
        return notALlRequestsCompleted(requestIndex)
                || !requests.isEmpty();
    }

    private boolean notALlRequestsCompleted(int requestIndex) {
        return requestIndex < numberOfRequests;
    }
}
