package Schedulers.Strategies;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class FD_SCAN extends Scheduler {
    boolean scanning = false;

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

        if(headFoundRequest() && scanning){    // scan
            Request temp = currentRequest;
            currentRequest = getHeadRequest();

            executeOrKillRequest(time);

            currentRequest = temp;
        }

        if(!currentRequestIsExecutedOrKilled()){
            executeOrKillRequest(time);
        }

        while(currentRequestIsExecutedOrKilled() && !requestQueue.isEmpty()){
            if(findShortestFeasibleDeadline(time)){
                startRequest(time);
                executeRequestIfHeadReachedAddress(time);

                scanning = requestQueueHasDeadline();
            }
            else{   // no reachable requests
                createGenesisRequest();
                scanning = false;
            }
        }

        requestQueue.forEach(Request::updateDeadline);
        moveHead();
    }

    private void executeOrKillRequest(int time) {
        if(headReachedAddress()){
            executeRequest(time);
            updateStatistics();
        }
        else if(deadlineExistsAndAchieved()){
            killRequest(time);
            updateStatistics();
        }
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of killed requests: %d (%.0f%%)\n", numberOfKilledRequests, 100.0*numberOfKilledRequests/numberOfRequests);
    }

    private boolean findShortestFeasibleDeadline(int time) {
        currentRequest = requestQueue.peek();
        while(currentRequest != null){
            currentRequest.calculateDistanceFromHead(disk.getHead());
            if(earliestDeadlineIsNotReachable()){
                killRequest(time);
                currentRequest = requestQueue.peek();
            }
            else{
                return true;
            }
        }
        return false;
    }

    private boolean earliestDeadlineIsNotReachable() {
        currentRequest.calculateDistanceFromHead(disk.getHead());
        return currentRequest.getDistanceFromHead() > currentRequest.getDeadline();
    }

    private boolean deadlineExistsAndAchieved() {
        return currentRequest.hasDeadline() && currentRequest.isDeadlineAchieved();
    }

    private boolean currentRequestIsExecutedOrKilled() {
        return currentRequest.isExecuted() || currentRequest.isKilled();
    }
}
