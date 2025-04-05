package Simulation;

public class Disk {
    public final int SIZE;

    private int head;

    public Disk(int size) {
        SIZE = size;
        head = 0;
    }

    public boolean canMoveHeadLeft(){
        return head + 1 < SIZE;
    }

    public void moveHeadLeft(){
        head++;
    }

    public boolean canMoveHeadRight(){
        return head > 0;
    }

    public void moveHeadRight(){
        head--;
    }

    public boolean accessData(){
        return head >= 0 && head < SIZE;
    }
}
