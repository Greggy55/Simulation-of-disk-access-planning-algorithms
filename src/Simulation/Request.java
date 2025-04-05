package Simulation;

public class Request {
    private final String NAME;
    private final int ARRIVAL_TIME;
    private final int ADDRESS;

    private boolean hasArrived = false;

    private final boolean hasDeadline;
    private int deadline;

    private int waitTime = 0;

    private int executionTime = -1;
    private boolean executed = false;

    public Request(String name, int arrivalTime, int address, int deadline) {
        this.NAME = name;
        this.ARRIVAL_TIME = arrivalTime;
        this.ADDRESS = address;

        this.deadline = deadline;
        hasDeadline = true;
    }

    public Request(String name, int arrivalTime, int address) {
        this.NAME = name;
        this.ARRIVAL_TIME = arrivalTime;
        this.ADDRESS = address;

        this.deadline = -1;
        hasDeadline = false;
    }

    public void execute(int time){
        if(hasArrived){
            executionTime = time;
            executed = true;
        }
    }

    public void update(int time){
        if(!hasArrived && ARRIVAL_TIME <= time){
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

    public String getName() {
        return NAME;
    }

    public int getArrivalTime() {
        return ARRIVAL_TIME;
    }

    public int getAddress() {
        return ADDRESS;
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
}
