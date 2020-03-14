package oop.encapsulation.practice3;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Timer t = new Timer();
        t.startTime();

        String message = "task.";
        for (int i = 0; i < 5; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < i; j++) {
                builder.append('.');
            }
            System.out.println(message.concat(builder.toString()));
            Thread.sleep(1000);
        }

        t.stopTime();
        t.printElapsedTime(TimeUnit.MILLISECONDS);
    }


}
