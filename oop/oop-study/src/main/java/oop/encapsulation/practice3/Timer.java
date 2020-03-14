package oop.encapsulation.practice3;

public class Timer {
    private long startTime;
    private long stopTime;

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void stopTime() {
        stopTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void printElaspedTime() {
        System.out.println("elaspedTime: " + (getStopTime() - getStartTime()));
    }
}
