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
                      int percentOfProcessesWithDeadline,
                      boolean[] print
    ) {
        this.numberOfRequests = numberOfRequests;
        this.maxArrivalTime = maxArrivalTime;
        this.diskSize = diskSize;
        this.maxDeadlineTime = maxDeadlineTime;

        if(percentOfProcessesWithDeadline <= 0){
            percentOfProcessesWithDeadline = 1;
        }
        else if(percentOfProcessesWithDeadline > 100){
            percentOfProcessesWithDeadline = 100;
        }

        this.percentOfProcessesWithDeadline = percentOfProcessesWithDeadline;

        requests = new ArrayList<>();

        fcfs = new FCFS(print[0], diskSize);
        //sstf = new SSTF(print[1], diskSize);
        //scan = new SCAN(print[2], diskSize);
        //cScan = new C_SCAN(print[3], diskSize);

        //edf = new EDF(print[4], diskSize);
        //fdScan = new FD_SCAN(print[5], diskSize);
    }

    public void start(){
        generateRequests();
        for(Request request : requests){System.out.println(request);}

        while(requestsExist()){
            //System.out.println(requests.isEmpty() + " " + fcfs.isEmpty());
            //System.out.println(fcfs.getRequests());
            addRequests();

            fcfs.schedule(time);

            // głowica zawsze się porusza
            time += TIME_UNIT;
        }

        System.out.println("---------------- FCFS -----------------");
        System.out.println("Average waiting time: " + 1.0 * fcfs.getTotalWaitTime() / numberOfRequests);
        System.out.println("Longest waiting time: " + fcfs.getLongestWaitTime());
    }

    private boolean requestHasArrived() {
        return requests.getFirst().getArrivalTime() == time;
    }

    private void addRequests(){
        while(!requests.isEmpty() && requestHasArrived()){
            fcfs.add(new Request(requests.getFirst()));
            //sstf.add(new Request(requests.getFirst()));
            //scan.add(new Request(requests.getFirst()));
            //cScan.add(new Request(requests.getFirst()));

            requests.removeFirst();
        }
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

    private boolean requestsExist() {
        return !requests.isEmpty() || !fcfs.isEmpty();
    }
}
