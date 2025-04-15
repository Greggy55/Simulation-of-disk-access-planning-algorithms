package Schedulers.Algorithms;

import Comp.CompoundComparator;
import Schedulers.Scheduler;
import Simulation.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SSTF extends Scheduler {
    public SSTF(boolean print, int diskSize) {
        super(print, diskSize, "SSTF");

        comparator.addComparator(Comparator.comparingInt(Request::getDistanceFromHead));
    }

    private void calculateDistanceFromHeadForAllRequests() {
        List<Request> updated = new ArrayList<>(requestQueue);
        updated.forEach(request -> request.calculateDistanceFromHead(disk.getHead()));
        requestQueue = new PriorityQueue<>(comparator);
        requestQueue.addAll(updated);
    }

    @Override
    public void schedule(int time) {
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
        }

        if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        while(currentRequest.isExecuted() && !requestQueue.isEmpty()){
            calculateDistanceFromHeadForAllRequests();
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of starved requests: %d\n", numberOfStarvedRequests);
    }
}
