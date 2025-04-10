package Schedulers.Algorithms;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class SSTF extends Scheduler {
    public SSTF(boolean print, int diskSize) {
        super(print, diskSize, "SSTF");

        comparator.addComparator(Comparator.comparingInt(Request::getDistanceFromHead));
    }

    private void calculateDistanceFromHeadForAllRequests() {
        requests.forEach(request -> request.calculateDistanceFromHead(disk.getHead()));
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
            int i = 0;
            for(Request request : requests){
                System.out.println(++i + ". " + request);
            }
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }
}
