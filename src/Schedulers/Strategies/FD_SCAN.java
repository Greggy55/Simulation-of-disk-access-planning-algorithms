package Schedulers.Strategies;

import Comp.CompoundComparator;
import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class FD_SCAN extends Scheduler {
    boolean isFCFSActive = false;

    public FD_SCAN(boolean print, int diskSize) {
        super(print, diskSize, "FD-SCAN");

        comparator.addComparator(Comparator.comparingInt(Request::getDeadline));
        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
    }

    @Override
    public void schedule(int time) {
        if(print){
            System.out.printf("(%2d %s) \tHead: " + disk.getHead() + "\n", time, name);
        }

        if(isFCFSActive && requestQueueHasDeadline()){   // set FD-SCAN
            isFCFSActive = false;
            //name = "FD-SCAN";
            comparator = new CompoundComparator<>();
            comparator.addComparator(Comparator.comparingInt(Request::getDeadline));
            comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
        }
        else if (!isFCFSActive && !requestQueueHasDeadline()){  // set FCFS
            isFCFSActive = true;
            //name = "FCFS";
            comparator = new CompoundComparator<>();
            comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
        }

        if(!isFCFSActive && headFoundRequest()){    // scan
            Request temp = currentRequest;
            currentRequest = getHeadRequest();

            executeOrKillRequest(time);

            currentRequest = temp;
        }

        if(!currentRequestIsExecutedOrKilled()){
            executeOrKillRequest(time);
        }

        while(currentRequestIsExecutedOrKilled() && !requestQueue.isEmpty()){
            if(!isFCFSActive){
                findShortestFeasibleDeadline(time);
                if(currentRequest == null){
                    createGenesisRequest();
                    continue;
                }
            }
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        requestQueue.forEach(Request::updateDeadline);
        moveHead();
    }

    private void executeOrKillRequest(int time) {
        if(deadlineExistsAndAchieved()){
            killRequest(time);
            updateStatistics();
        }
        else{
            executeRequestIfHeadReachedAddress(time);
        }
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of killed requests: %d\n", numberOfKilledRequests);
    }

    private void findShortestFeasibleDeadline(int time) {
        currentRequest = requestQueue.peek();
        while(currentRequest != null && earliestDeadlineIsNotReachable()){
            killRequest(time);
            currentRequest = requestQueue.peek();
        }
    }

    private boolean earliestDeadlineIsNotReachable() {
        currentRequest.calculateDistanceFromHead(disk.getHead());
        return currentRequest.getDistanceFromHead() < currentRequest.getDeadline();
    }

    private boolean deadlineExistsAndAchieved() {
        return currentRequest.hasDeadline() && currentRequest.isDeadlineAchieved();
    }

    private boolean currentRequestIsExecutedOrKilled() {
        return currentRequest.isExecuted() || currentRequest.isKilled();
    }
}
