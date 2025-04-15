package Simulation;

import Schedulers.Algorithms.*;
import Schedulers.Strategies.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation {
    private static final int TIME_UNIT = 1;

    private final int diskSize;
    private final int numberOfRequests;
    private final int maxArrivalTime;
    private final int maxDeadlineTime;
    private final int percentOfProcessesWithDeadline;

    private final int numberOfBehindHeadRequests;

    private final int numberOfCloseTogetherRequests;
    private final int centerOfCloseTogetherRequests;
    private final int radiusOfCloseTogetherRequests;

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
                      int numberOfBehindHeadRequests,
                      int numberOfCloseTogetherRequests,
                      int centerOfCloseTogetherRequests,
                      int radiusOfCloseTogetherRequests,
                      boolean[] print
    ) {
        this.numberOfRequests = numberOfRequests;
        this.maxArrivalTime = maxArrivalTime;
        this.diskSize = diskSize;
        this.maxDeadlineTime = maxDeadlineTime;

        this.numberOfBehindHeadRequests = Math.min(numberOfBehindHeadRequests, numberOfRequests);
        this.numberOfCloseTogetherRequests = Math.min(numberOfCloseTogetherRequests, numberOfRequests);
        this.centerOfCloseTogetherRequests = Math.min(centerOfCloseTogetherRequests, diskSize);
        this.radiusOfCloseTogetherRequests = Math.max(radiusOfCloseTogetherRequests, 0);

        if(percentOfProcessesWithDeadline < 0){
            percentOfProcessesWithDeadline = 0;
        }
        else if(percentOfProcessesWithDeadline > 100){
            percentOfProcessesWithDeadline = 100;
        }

        this.percentOfProcessesWithDeadline = percentOfProcessesWithDeadline;

        requests = new ArrayList<>();

        fcfs = new FCFS(print[0], diskSize);
        sstf = new SSTF(print[1], diskSize);
        scan = new SCAN(print[2], diskSize);
        cScan = new C_SCAN(print[3], diskSize);

        edf = new EDF(print[4], diskSize);
        fdScan = new FD_SCAN(print[5], diskSize);
    }

    public void start(){
        generateRequests();
        printAllRequests();

        int countOfBehindHeadRequests = 0;

        while(requestsExist()){
            addRequests();

            if(canAddBehindHeadRequest(countOfBehindHeadRequests)){
                addBehindHeadRequests();
                countOfBehindHeadRequests++;
            }

            fcfs.schedule(time);
            sstf.schedule(time);
            scan.schedule(time);
            cScan.schedule(time);

            edf.schedule(time);
            fdScan.schedule(time);

            // head always moves
            time += TIME_UNIT;
        }

        edf.printStatistics(numberOfRequests);
        fdScan.printStatistics(numberOfRequests);

        fcfs.printStatistics(numberOfRequests);
        sstf.printStatistics(numberOfRequests);
        scan.printStatistics(numberOfRequests);
        cScan.printStatistics(numberOfRequests);
    }

    private void addBehindHeadRequests() {
        fcfs.addBehindHead(time);
        sstf.addBehindHead(time);
        scan.addBehindHead(time);
        cScan.addBehindHead(time);

        edf.addBehindHead(time);
        fdScan.addBehindHead(time);
    }

    private boolean canAddBehindHeadRequest(int countOfBehindHeadRequests) {
        return requestIsDrawn() && limitOfBehindHeadRequestsIsNotAchieved(countOfBehindHeadRequests);
    }

    private boolean limitOfBehindHeadRequestsIsNotAchieved(int countOfBehindHeadRequests) {
        return countOfBehindHeadRequests < numberOfBehindHeadRequests;
    }

    private boolean requestIsDrawn() {
        return rnd.nextInt(numberOfRequests) < time;
    }

    private void printAllRequests() {
        int[] count = new int[diskSize];

        for(Request request : requests){
            System.out.println(request);
            count[request.getAddress()]++;
        }

        System.out.println(Arrays.toString(count));
    }

    private boolean requestHasArrived() {
        return !requests.isEmpty() && requests.getFirst().getArrivalTime() == time;
    }

    private void addRequests(){
        while(requestHasArrived()){
            fcfs.add(new Request(requests.getFirst()));
            sstf.add(new Request(requests.getFirst()));
            scan.add(new Request(requests.getFirst()));
            cScan.add(new Request(requests.getFirst()));

            edf.add(new Request(requests.getFirst()));
            fdScan.add(new Request(requests.getFirst()));

            requests.removeFirst();

            if(requests.isEmpty()){
                fcfs.setHalt(true);
                sstf.setHalt(true);
                scan.setHalt(true);
                cScan.setHalt(true);

                edf.setHalt(true);
                fdScan.setHalt(true);
            }
        }
    }

    private int generateArtificialRequests(){
        int count = 0;

        Request request = new Request(0,10,10);
        requests.add(request);
        count++;

        return count;
    }

    private int shift(){
        int val = 0;
        val = generateArtificialRequests();
        return val;
    }

    private void generateRequests(){
        for(int i = shift(); i < numberOfRequests - numberOfBehindHeadRequests - numberOfCloseTogetherRequests; i++){
            generateRegularRequest();
        }

        for(int i = 0; i < numberOfCloseTogetherRequests; i++){
            generateCloseTogetherRequest();
        }
        
        // sort ascending by arrival time
        requests.sort((r1, r2) -> Integer.compare(r1.getArrivalTime(), r2.getArrivalTime()));
    }

    private void generateCloseTogetherRequest() {

        int begin = Math.max(centerOfCloseTogetherRequests - radiusOfCloseTogetherRequests, 0);
        int end = Math.min(centerOfCloseTogetherRequests + radiusOfCloseTogetherRequests, diskSize);

        if(generateWithDeadline()){
            Request request = new Request(
                    maxArrivalTime / 2,
                    rnd.nextInt(begin, end),
                    rnd.nextInt(maxDeadlineTime)
            );
            requests.add(request);
        }
        else{
            Request request = new Request(
                    maxArrivalTime / 2,
                    rnd.nextInt(begin, end)
            );
            requests.add(request);
        }
    }

    private void generateRegularRequest() {
        int address = rnd.nextInt(diskSize);
        if(numberOfCloseTogetherRequests > 0){
            int begin = Math.max(centerOfCloseTogetherRequests - radiusOfCloseTogetherRequests, 0);
            int end = Math.min(centerOfCloseTogetherRequests + radiusOfCloseTogetherRequests, diskSize);
            while(begin <= address && address <= end){
                address = rnd.nextInt(diskSize);
            }
        }

        if(generateWithDeadline()){
            Request request = new Request(
                    rnd.nextInt(maxArrivalTime),
                    address,
                    rnd.nextInt(maxDeadlineTime)
            );
            requests.add(request);
        }
        else{
            Request request = new Request(
                    rnd.nextInt(maxArrivalTime),
                    address
            );
            requests.add(request);
        }
    }

    private boolean generateWithDeadline() {
        return rnd.nextInt(100) < percentOfProcessesWithDeadline;
    }

    private boolean requestsExist() {
        return !requests.isEmpty()
                || !fcfs.isEmpty()
                || !sstf.isEmpty()
                || !scan.isEmpty()
                || !cScan.isEmpty()
                || !edf.isEmpty()
                || !fdScan.isEmpty();
    }
}
