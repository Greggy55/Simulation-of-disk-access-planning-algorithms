package Schedulers.Algorithms;

import Schedulers.Scheduler;
import Simulation.Request;

import java.util.Comparator;

public class SCAN extends Scheduler {
    public SCAN(boolean print, int diskSize) {
        super(print, diskSize, "SCAN");

        comparator.addComparator(Comparator.comparingInt(Request::getArrivalTime));
    }

    @Override
    public void schedule(int time) {
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
        }

        if(headFoundRequest()){
            currentRequest = getHeadRequest();
            executeRequest(time);
        }
        System.out.println(requestQueue);
        moveHead();
    }
}
