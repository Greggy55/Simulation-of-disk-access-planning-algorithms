package Algorithms;

import Simulation.Request;

import java.util.LinkedList;
import java.util.Queue;

public class FCFS {
    private Queue<Request> requests = new LinkedList<>();
    private Request currentRequest;

    private int totalWaitTime = 0;
    private int longestWaitTime = 0;

    private final boolean print;

    public FCFS(boolean print){
        this.print = print;

        currentRequest = new Request(0,0);
        currentRequest.execute(0);
    }

    public void schedule(int time){

    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public int getTotalWaitTime() {
        return totalWaitTime;
    }

    public int getLongestWaitTime() {
        return longestWaitTime;
    }
}
