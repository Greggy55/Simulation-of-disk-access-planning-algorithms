package Schedulers.Algorithms;

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
        List<Request> updated = new ArrayList<>(requests);
        updated.forEach(request -> request.calculateDistanceFromHead(disk.getHead()));
        requests = new PriorityQueue<>(comparator);
        requests.addAll(updated);
    }

    @Override
    public void schedule(int time) {
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
        }

        if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        while(currentRequest.isExecuted() && !requests.isEmpty()){
            calculateDistanceFromHeadForAllRequests();
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

    private void printRequests() {
        int i = 0;
        for(Request request : requests){
            System.out.println(++i + ". " + request);
        }
    }
}
