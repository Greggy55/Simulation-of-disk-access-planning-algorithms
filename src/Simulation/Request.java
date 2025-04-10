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
    private int executionTime = -1;

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
        this.hasDeadline = request.hasDeadline;
        this.killed = request.killed;
        this.killTime = request.killTime;
    }

    public void execute(int time){
        executionTime = time;
        waitTime = executionTime - arrivalTime;
        executed = true;
    }

    public void kill(int time){
        executionTime = time;
        waitTime = executionTime - arrivalTime;
        killed = true;
    }

    public void updateDeadline(){
        deadline--;
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

    public int getExecutionTime() {
        return executionTime;
    }

    public boolean isExecuted() {
        return executed;
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
            result += String.format("\texecutionTime=%-4d", executionTime);
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
