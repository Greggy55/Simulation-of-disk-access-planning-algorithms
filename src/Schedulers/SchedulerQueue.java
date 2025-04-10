package Schedulers;

import Simulation.Request;

import java.util.PriorityQueue;

public class SchedulerQueue extends Scheduler {
    protected PriorityQueue<Request> requestQueue;


    public SchedulerQueue(boolean print, int diskSize, String name) {
        super(print, diskSize, name);
    }

    @Override
    public void schedule(int time) {

    }
}
