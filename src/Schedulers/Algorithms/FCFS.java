package Schedulers.Algorithms;

import Comp.CompoundComparator;
import Schedulers.Scheduler;
import Simulation.Disk;
import Simulation.Request;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

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
        numberOfHeadMoves++;
    }

}
