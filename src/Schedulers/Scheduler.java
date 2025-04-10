package Schedulers;

import Comp.CompoundComparator;
import Simulation.Disk;
import Simulation.Request;

import java.util.PriorityQueue;

public abstract class Scheduler {
    protected final String name;

    protected PriorityQueue<Request> requestQueue;

    protected Request currentRequest;
    protected Request headRequest = null;

    protected Disk disk;
    protected CompoundComparator<Request> comparator;

    protected int totalWaitTime = 0;
    protected int longestWaitTime = 0;
    protected int numberOfHeadMoves = 0;

    protected boolean halt = false;

    private boolean movingRight = true;

    protected boolean print;

    public Scheduler(boolean print, int diskSize, String name) {
        this.print = print;
        disk = new Disk(diskSize);
        this.name = name;

        comparator = new CompoundComparator<>();
        requestQueue = new PriorityQueue<>(comparator);

        currentRequest = new Request(0,0, 0);
        currentRequest.execute(0);
    }

    public void add(Request request){
        requestQueue.offer(request);
    }

    public boolean isEmpty(){
        return requestQueue.isEmpty();
    }

    public abstract void schedule(int time);

    public void checkMovementDirection(){
        if(movingRight && !disk.canMoveHeadRight()){
            movingRight = false;
        }
        if(!movingRight && !disk.canMoveHeadLeft()){
            movingRight = true;
        }
    }

    public void moveHead() {
        if(requestQueue.isEmpty()){
            if(!halt){
                checkMovementDirection();
                if(movingRight){
                    disk.moveHeadRight();
                }
                else{
                    disk.moveHeadLeft();
                }
                numberOfHeadMoves++;
            }
        }
        else if(requestAddressIsOnTheRightSideOfTheHead()){
            if(!disk.canMoveHeadRight()){
                throw new IllegalStateException("Can't move head right side");
            }
            disk.moveHeadRight();
            numberOfHeadMoves++;
        }
        else if(requestAddressIsOnTheLeftSideOfTheHead()){
            if(!disk.canMoveHeadLeft()){
                throw new IllegalStateException("Can't move head left side");
            }
            disk.moveHeadLeft();
            numberOfHeadMoves++;
        }
        else{
            throw new RuntimeException("Should never execute this command");
        }
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
        requestQueue.remove(currentRequest);
        if(print){
            System.out.printf("(%2d %s) \tExecuted:\t" + currentRequest + "\n", time, name);
        }
        stopPrintingIfDone();
    }

    private void stopPrintingIfDone() {
        if(halt && isEmpty()){
            print = false;
        }
    }

    public void startRequest(int time) {
        currentRequest = requestQueue.peek();
        if(currentRequest == null){
            throw new IllegalStateException("Current request should never be null");
        }
        if(print){
            System.out.printf("(%2d %s) \tStarted:\t" + currentRequest + "\n", time, name);
        }
    }

    public void killRequest(int time) {
        currentRequest.kill(time);
        requestQueue.remove(currentRequest);
        if(print){
            System.out.printf("(%2d %s) \tKilled:\t" + currentRequest + "\n", time, name);
        }
        stopPrintingIfDone();
    }

    public void updateStatistics(){
        totalWaitTime += currentRequest.getWaitTime();
        longestWaitTime = Math.max(longestWaitTime, currentRequest.getWaitTime());
    }

    public void printStatistics(int numberOfRequests) {
        final int dashes = 15;
        System.out.printf("%s %s %s\n", "-".repeat(dashes), name, "-".repeat(dashes - name.length() + dashes/3));
        System.out.printf("Average waiting time: %.2f\n", 1.0 * totalWaitTime / numberOfRequests);
        System.out.printf("Longest waiting time: %d\n", longestWaitTime);
        System.out.printf("Number of head moves: %d\n", numberOfHeadMoves);
    }

    public boolean headFoundRequest() {
        for (Request request : requestQueue) {
            if (disk.getHead() == request.getAddress()) {
                headRequest = request;
                return true;
            }
        }

        return false;
    }

    public Request getHeadRequest() {
        Request returnRequest = headRequest;
        headRequest = null;
        return returnRequest;
    }

    public boolean headReachedAddress() {
        return disk.getHead() == currentRequest.getAddress();
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }
}
