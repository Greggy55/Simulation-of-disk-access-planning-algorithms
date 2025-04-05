package Simulation;

public class Request {
    private final int arrivalTime;
    private final int address;

    private boolean hasArrived = false;

    private final boolean hasDeadline;
    private int deadline;

    private int waitTime = 0;

    private int executionTime = -1;
    private boolean executed = false;

    public Request(int arrivalTime, int address, int deadline) {
        this.arrivalTime = arrivalTime;
        this.address = address;

        this.deadline = deadline;
        hasDeadline = true;
    }

    public Request(int arrivalTime, int address) {
        this.arrivalTime = arrivalTime;
        this.address = address;

        this.deadline = -1;
        hasDeadline = false;
    }

    public Request(Request request){
        this.arrivalTime = request.arrivalTime;
        this.address = request.address;
        this.deadline = request.deadline;
        this.waitTime = request.waitTime;
        this.executionTime = request.executionTime;
        this.executed = request.executed;
        this.hasArrived = request.hasArrived;
        this.hasDeadline = request.hasDeadline;
    }

    public void execute(int time){
        if(!hasArrived){
            throw new IllegalStateException("Executed request has not arrived yet");
        }
        executionTime = time;
        executed = true;
    }

    public void update(int time){
        if(!hasArrived && arrivalTime <= time){
            hasArrived = true;
        }
        // maybe swap?
        if(hasArrived){
            if(!executed){
                waitTime++;
                if(hasDeadline){
                    deadline--;
                }
            }

        }
    }

    public boolean hasArrived(){
        return hasArrived;
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

    public int getExecutionTime() {
        return executionTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    @Override
    public String toString() {
        return String.format("request: " +
                "arrivalTime=%-4d" +
                "\taddress=%-4d" +
                "\twaitTime=%-4d" +
                (hasDeadline ? ("\tdeadline=%-4d") : "") +
                (executed ? ("\texecutionTime=%-4d") : ""),
                arrivalTime, address, waitTime, deadline, executionTime
        );
    }
}
