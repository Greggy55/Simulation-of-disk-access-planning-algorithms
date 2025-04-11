package Schedulers.Algorithms;

public class C_SCAN extends SCAN {
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
        }
        numberOfHeadMoves++;
    }
}
