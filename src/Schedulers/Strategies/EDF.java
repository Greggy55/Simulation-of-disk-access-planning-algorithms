package Schedulers.Strategies;

import Comp.CompoundComparator;
import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;
import java.util.PriorityQueue;

public class EDF extends Scheduler {

    public EDF(boolean print, int diskSize){
        super(print, diskSize, "EDF");

        comparator = new CompoundComparator<>();
        comparator.addComparator(Comparator.comparingInt(Request::getDeadline));
        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));

        requestQueue = new PriorityQueue<>(comparator);
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d %s) \tHead: " + disk.getHead() + "\n", time, name);
        }

        if(currentRequest.isDeadlineAchieved()){
            killRequest(time);
        }
        else if(!currentRequest.isExecuted()){
            executeRequestIfHeadReachedAddress(time);
        }

        while(currentRequest.isExecuted() && !requestQueue.isEmpty()){
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

}
