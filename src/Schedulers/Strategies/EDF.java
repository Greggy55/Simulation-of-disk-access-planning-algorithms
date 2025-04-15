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

        if(!currentRequestIsExecutedOrKilled()){
            executeOrKillRequest(time);
        }

        while(currentRequestIsExecutedOrKilled() && !requestQueue.isEmpty()){
            if(findShortestDeadline(time)){
                startRequest(time);
                executeRequestIfHeadReachedAddress(time);
            }
            else{
                createGenesisRequest();
            }
        }

        requestQueue.forEach(Request::updateDeadline);
        moveHead();
    }

    private void executeOrKillRequest(int time) {
        if(headReachedAddress()){
            executeRequest(time);
            updateStatistics();
        }
        else if(deadlineExistsAndAchieved()){
            killRequest(time);
            updateStatistics();
        }
    }

    private boolean findShortestDeadline(int time) {
        currentRequest = requestQueue.peek();
        while(currentRequest != null){
            if(currentRequest.isDeadlineAchieved()){
                killRequest(time);
                currentRequest = requestQueue.peek();
            }
            else{
                return true;
            }
        }
        return false;
    }

    private boolean currentRequestIsExecutedOrKilled() {
        return currentRequest.isExecuted() || currentRequest.isKilled();
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of killed requests: %d (%.0f%%)\n", numberOfKilledRequests, 100.0*numberOfKilledRequests/numberOfRequests);
    }

    private boolean deadlineExistsAndAchieved() {
        return currentRequest.hasDeadline() && currentRequest.isDeadlineAchieved();
    }

}
