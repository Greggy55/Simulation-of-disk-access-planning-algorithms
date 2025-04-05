package Simulation;

import Algorithms.*;
import Strategies.*;

import java.util.Random;

public class Simulation {
    private final int DISK_SIZE;
    private final int TIME_UNIT;
    private final int NUMBER_OF_REQUESTS;

    Random rnd = new Random();

    private FCFS fcfs;
    private SSTF sstf;
    private SCAN scan;
    private C_SCAN cScan;

    private EDF edf;
    private FD_SCAN fdScan;

    private int time = 0;

    public Simulation(int numberOfRequests, int diskSize, int timeUnit) {
        NUMBER_OF_REQUESTS = numberOfRequests;
        DISK_SIZE = diskSize;
        TIME_UNIT = timeUnit;
    }

    public void start(){}
}
