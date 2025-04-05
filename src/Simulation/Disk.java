package Simulation;

public class Disk {
    public final int size;

    private int head;

    public Disk(int size) {
        this.size = size;
        head = 0;
    }

    public int getHead() {
        if(head < 0 || head >= size){
            throw new IllegalStateException("Disk head out of bounds");
        }
        return head;
    }

    public boolean canMoveHeadLeft(){
        return head > 0;
    }

    public void moveHeadLeft(){
        head--;
    }

    public boolean canMoveHeadRight(){
        return head + 1 < size;
    }

    public void moveHeadRight(){
        head++;
    }

    public boolean accessData(){
        return head >= 0 && head < size;
    }
}
