package Schedulers.Strategies;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;
import java.util.PriorityQueue;

public class EDF extends Scheduler {

    public EDF(boolean print, int diskSize){
        super(print, diskSize, "EDF");

        comparator.addComparator(Comparator.comparingInt(Request::getDeadline));
        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));

        requests = new PriorityQueue<>(comparator);

        currentRequest = new Request(0,0);
        currentRequest.execute(0);
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d %s) \tHead: " + disk.getHead() + "\n", time, name);
        }

        if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        while(currentRequest.isExecuted() && !requests.isEmpty()){
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

}
