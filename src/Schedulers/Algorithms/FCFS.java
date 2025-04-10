package Schedulers.Algorithms;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class FCFS extends Scheduler {

    public FCFS(boolean print, int diskSize){
        super(print, diskSize, "FCFS");

        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
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
