package Simulation;

public class Request {
    private final int arrivalTime;
    private final int address;

    private final boolean hasDeadline;
    private int deadline;

    private boolean killed = false;
    private int killTime = -1;

    private int waitTime = 0;

    private boolean executed = false;
    private int stopTime = -1;

    private int distanceFromHead = 0;

    public Request(int arrivalTime, int address, int deadline) {
        this.arrivalTime = arrivalTime;
        this.address = address;

        this.deadline = deadline;
        hasDeadline = true;
    }

    public Request(int arrivalTime, int address) {
        this.arrivalTime = arrivalTime;
        this.address = address;

        this.deadline = Integer.MAX_VALUE;
        hasDeadline = false;
    }

    public Request(Request request){
        this.arrivalTime = request.arrivalTime;
        this.address = request.address;
        this.deadline = request.deadline;
        this.waitTime = request.waitTime;
        this.stopTime = request.stopTime;
        this.executed = request.executed;
        this.hasDeadline = request.hasDeadline;
        this.killed = request.killed;
        this.killTime = request.killTime;
        this.distanceFromHead = request.distanceFromHead;
    }

    public void execute(int time){
        stopTime = time;
        waitTime = stopTime - arrivalTime;
        executed = true;
    }

    public void kill(int time){
        stopTime = time;
        waitTime = stopTime - arrivalTime;
        killed = true;
    }

    public void updateDeadline(){
        if(hasDeadline){
            deadline--;
        }
    }

    public boolean isDeadlineAchieved(){
        return deadline < 0;
    }

    public boolean isKilled() {
        return killed;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getAddress() {
        return address;
    }

    public boolean hasDeadline() {
        return hasDeadline;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    public int getDistanceFromHead(){
        return distanceFromHead;
    }

    public void calculateDistanceFromHead(int headAddress) {
        distanceFromHead = Math.abs(headAddress - address);
    }

    @Override
    public String toString() {
        String result = String.format(
                        "arrivalTime=%-4d" +
                        "\taddress=%-4d" +
                        "\twaitTime=%-4d",
                        arrivalTime, address, waitTime
                );

        if(executed){
            result += String.format("\texecutionTime=%-4d", stopTime);
        }
        else if(killed){
            result += "\texecutionTime=kill";
        }
        else{
            result += "\texecutionTime=????";
        }

        if(hasDeadline){
            result += String.format("\tdeadline=%-4d", deadline);
        }

        return result;
    }
}
