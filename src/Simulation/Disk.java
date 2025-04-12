package Simulation;

public class Disk {
    public final int size;

    private int head;
    private int headPrev;

    public Disk(int size) {
        this.size = size;
        head = 0;
        headPrev = -1;
    }

    public int getHead() {
        if(head < 0 || head >= size){
            throw new IllegalStateException("Disk head out of bounds");
        }
        return head;
    }

    public int getHeadPrev() {
        return headPrev;
    }

    public void setHead(int head) {
        if(headAddressIsValid()){
            headPrev = this.head;
            this.head = head;
        }
    }

    public boolean canMoveHeadLeft(){
        return head > 0;
    }

    public void moveHeadLeft(){
        headPrev = head;
        head--;
    }

    public boolean canMoveHeadRight(){
        return head + 1 < size;
    }

    public void moveHeadRight(){
        headPrev = head;
        head++;
    }

    public boolean headAddressIsValid(){
        return head >= 0 && head < size;
    }
}
