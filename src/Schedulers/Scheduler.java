package Schedulers;

import Comp.CompoundComparator;
import Simulation.Disk;
import Simulation.Request;

import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Scheduler {
    protected final String name;

    protected PriorityQueue<Request> requests;
    protected Request currentRequest;
    protected Disk disk;
    protected CompoundComparator<Request> comparator;

    protected int totalWaitTime = 0;
    protected int longestWaitTime = 0;
    protected int numberOfHeadMoves = 0;

    protected final boolean print;

    public Scheduler(boolean print, int diskSize, String name) {
        this.print = print;
        disk = new Disk(diskSize);
        this.name = name;

        comparator = new CompoundComparator<>();
        requests = new PriorityQueue<>(comparator);

        currentRequest = new Request(0,0);
        currentRequest.execute(0);
    }

    public void add(Request request){
        requests.offer(request);
    }

    public boolean isEmpty(){
        return requests.isEmpty();
    }

    public abstract void schedule(int time);

    public void moveHead() {
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

        numberOfHeadMoves++;
    }

    public boolean requestAddressIsOnTheRightSideOfTheHead() {
        return currentRequest.getAddress() > disk.getHead();
    }

    public boolean requestAddressIsOnTheLeftSideOfTheHead() {
        return currentRequest.getAddress() < disk.getHead();
    }

    public void executeRequestIfHeadReachedAddress(int time) {
        if(headReachedAddress()){
            executeRequest(time);
            updateStatistics();
        }
    }

    public void executeRequest(int time) {
        currentRequest.execute(time);
        requests.remove(currentRequest);
        if(print){
            System.out.printf("(%2d %s) \tExecuted:\t" + currentRequest + "\n", time, name);
        }
    }

    public void startRequest(int time) {
        currentRequest = requests.peek();
        if(currentRequest == null){
            throw new IllegalStateException("Current request should never be null");
        }
        if(print){
            System.out.printf("(%2d %s) \tStarted:\t" + currentRequest + "\n", time, name);
        }
    }

    public void updateStatistics(){
        totalWaitTime += currentRequest.getWaitTime();
        longestWaitTime = Math.max(longestWaitTime, currentRequest.getWaitTime());
    }

    public void getStatistics(int numberOfRequests) {
        System.out.println("---------------- " + name + " -----------------");
        System.out.println("Average waiting time: " + 1.0 * totalWaitTime / numberOfRequests);
        System.out.println("Longest waiting time: " + longestWaitTime);
    }

    public boolean headReachedAddress() {
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
