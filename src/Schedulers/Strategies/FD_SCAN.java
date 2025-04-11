package Schedulers.Strategies;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class FD_SCAN extends Scheduler {
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

        if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        if(headFoundRequest()){
            Request temp = currentRequest;

            currentRequest = getHeadRequest();
            executeRequestIfHeadReachedAddress(time);

            currentRequest = temp;
        }

        while(currentRequest.isExecuted() && !requestQueue.isEmpty()){
            findShortestFeasibleDeadline(time);
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

    private void findShortestFeasibleDeadline(int time) {
        currentRequest = requestQueue.peek();
        while(earliestDeadlineIsNotReachable()){
            killRequest(time);
            currentRequest = requestQueue.peek();
        }
    }

    private boolean earliestDeadlineIsNotReachable() {
        currentRequest.calculateDistanceFromHead(disk.getHead());
        return currentRequest.getDistanceFromHead() < currentRequest.getDeadline();
    }
}
