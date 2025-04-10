package Schedulers.Strategies;

import Schedulers.Scheduler;

public class FD_SCAN extends Scheduler {
    public FD_SCAN(boolean print, int diskSize) {
        super(print, diskSize, "FD-SCAN");
    }

    @Override
    public void schedule(int time) {

    }
}
