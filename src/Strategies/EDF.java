package Strategies;

import Simulation.Disk;
import Simulation.Request;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class EDF {
    private PriorityQueue<Request> requests;
    private Request currentRequest;
    private Disk disk;

    private int totalWaitTime = 0;
    private int longestWaitTime = 0;

    private final boolean print;

    public EDF(boolean print, int diskSize){
        this.print = print;

        requests = new PriorityQueue<>(new Comparator<Request>() {
            @Override
            public int compare(Request o1, Request o2) {
                int compare = Integer.compare(o1.getDeadline(), o2.getDeadline());
                if(compare == 0){
                    return Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());
                }
                return compare;
            }
        });

        currentRequest = new Request(0,0);
        currentRequest.execute(0);

        disk = new Disk(diskSize);
    }

    public void add(Request request){
        requests.offer(request);
    }

    public boolean isEmpty(){
        return requests.isEmpty();
    }

    public void schedule(int time){
        if(print){
            System.out.printf("(%2d EDF) \tHead: " + disk.getHead() + "\n", time);
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

    private void moveHead() {
        if(requests.isEmpty()){
            if(disk.canMoveHeadRight()){
                disk.moveHeadRight();
            }
            else{
                disk.moveHeadLeft();
            }
        }
        else if(requestAddressIsOnTheRightSideOfTheHead()){
            if(!disk.canMoveHeadRight()){
                throw new IllegalStateException("Can't move head right side");
            }
            disk.moveHeadRight();
        }
        else if(requestAddressIsOnTheLeftSideOfTheHead()){
            if(!disk.canMoveHeadLeft()){
                throw new IllegalStateException("Can't move head left side");
            }
            disk.moveHeadLeft();
        }
        else{
            throw new RuntimeException("Should never execute this command");
        }
    }

    private boolean requestAddressIsOnTheRightSideOfTheHead() {
        return currentRequest.getAddress() > disk.getHead();
    }

    private boolean requestAddressIsOnTheLeftSideOfTheHead() {
        return currentRequest.getAddress() < disk.getHead();
    }

    private void executeRequestIfHeadReachedAddress(int time) {
        if(headReachedAddress()){
            executeRequest(time);
            updateStatistics();
        }
    }

    private void executeRequest(int time) {
        currentRequest.execute(time);
        requests.poll();
        if(print){
            System.out.printf("(%2d EDF) \tExecuted:\t" + currentRequest + "\n", time);
        }
    }

    private void startRequest(int time) {
        currentRequest = requests.peek();
        if(currentRequest == null){
            throw new IllegalStateException("Current request should never be null");
        }
        if(print){
            System.out.printf("(%2d EDF) \tStarted:\t" + currentRequest + "\n", time);
        }
    }

    private void updateStatistics(){
        totalWaitTime += currentRequest.getWaitTime();
        longestWaitTime = Math.max(longestWaitTime, currentRequest.getWaitTime());
    }

    private boolean headReachedAddress() {
        return disk.getHead() == currentRequest.getAddress();
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

    public Queue<Request> getRequests() {
        return requests;
    }
}
