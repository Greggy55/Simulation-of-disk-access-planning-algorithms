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

        currentRequest.updateDeadline();

        if(!currentRequestIsExecutedOrKilled()){
            if(deadlineExistsAndAchieved()){
                killRequest(time);
                updateStatistics();
            }
            else{
                executeRequestIfHeadReachedAddress(time);
            }
        }

        while(currentRequestIsExecutedOrKilled() && !requestQueue.isEmpty()){
            startRequest(time);
            executeRequestIfHeadReachedAddress(time);
        }

        moveHead();
    }

    private boolean currentRequestIsExecutedOrKilled() {
        return currentRequest.isExecuted() || currentRequest.isKilled();
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of killed requests: %d\n", numberOfKilledRequests);
    }

    private boolean deadlineExistsAndAchieved() {
        return currentRequest.hasDeadline() && currentRequest.isDeadlineAchieved();
    }

}
