package Schedulers.Algorithms;

import Schedulers.Scheduler;

import java.util.ArrayList;

public class SCAN extends Scheduler {
    public SCAN(boolean print, int diskSize) {
        super(print, diskSize, "SCAN");

        requestList = new ArrayList<>(diskSize);
    }

    @Override
    public void schedule(int time) {
        if(print){
            System.out.printf("(%2d %s)\tHead: " + disk.getHead() + "\n", time, name);
        }

        moveHead();
    }
}
