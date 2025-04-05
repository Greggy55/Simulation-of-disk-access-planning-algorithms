package Simulation;

public class Disk {
    public final int SIZE;

    private int head;
    private String[] data;

    public Disk(int size) {
        SIZE = size;
        head = size / 2;
        data = new String[SIZE];

        for(int i = 0; i < size; i++) {
            data[i] = String.valueOf(i);
        }
    }

    public boolean canMoveLeft(){
        return head + 1 < SIZE;
    }

    public void moveLeft(){
        head++;
    }

    public boolean canMoveRight(){
        return head > 0;
    }

    public void moveRight(){
        head--;
    }

    public String getData(){
        if(head < 0 || head >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("head is out of bounds");
        }
        return data[head];
    }

    public void setData(String newData){
        if(head < 0 || head >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("head is out of bounds");
        }
        data[head] = newData;
    }
}
