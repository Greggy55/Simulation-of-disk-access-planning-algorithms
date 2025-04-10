package Schedulers.Strategies;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class EDF extends Scheduler {

    public EDF(boolean print, int diskSize){
        super(print, diskSize, "EDF");

        comparator.addComparator(Comparator.comparingInt(Request::getDeadline));
        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d %s) \tHead: " + disk.getHead() + "\n", time, name);
        }

        if(currentRequest.isDeadlineAchieved()){
            killRequest(time);
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
