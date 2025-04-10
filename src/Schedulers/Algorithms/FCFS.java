package Schedulers.Algorithms;

import Comp.CompoundComparator;
import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;
import java.util.PriorityQueue;

public class FCFS extends Scheduler {

    public FCFS(boolean print, int diskSize){
        super(print, diskSize, "FCFS");

        comparator = new CompoundComparator<>();
        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));

        requestQueue = new PriorityQueue<>(comparator);
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
        }

        if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        while(currentRequest.isExecuted() && !requestQueue.isEmpty()){
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

}
