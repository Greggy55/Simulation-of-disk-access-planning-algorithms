package Schedulers.Algorithms;

public class C_SCAN extends SCAN {
    private int numberOfJumps = 0;

    public C_SCAN(boolean print, int diskSize) {
        super(print, diskSize);
        name = "C-SCAN";
    }

    @Override
    public void sweep() {
        if(disk.getHead() < disk.size - 1){
            disk.moveHeadRight();
        }
        else{
            disk.setHead(0);
            numberOfJumps++;
        }
        numberOfHeadMoves++;
    }

    @Override
    public void printStatistics(int numberOfRequests) {
        super.printStatistics(numberOfRequests);
        System.out.printf("Number of jumps: %d\n", numberOfJumps);
    }

    public int getNumberOfJumps() {
        return numberOfJumps;
    }
}
