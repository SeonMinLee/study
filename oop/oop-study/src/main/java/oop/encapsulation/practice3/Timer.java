package oop.encapsulation.practice3;

import java.util.concurrent.TimeUnit;

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

    public void printElapsedTime(TimeUnit timeUnit) {

        long elapsedLong = getStopTime() - getStartTime();
        switch (timeUnit) {
            case SECONDS:
                System.out.println("elapsedSeconds: " + elapsedLong / 1000);
                break;
            case MILLISECONDS:
            default:
                System.out.println("elapsedMillis: " + elapsedLong);
                break;
        }
    }
}
