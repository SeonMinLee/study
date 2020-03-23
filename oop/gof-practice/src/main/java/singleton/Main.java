package singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TicketMaker ticketMaker = TicketMaker.getInstance();
        Runnable task = () -> {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                int nextTicketNumber = ticketMaker.getNextTicketNumber();
                System.out.println(Thread.currentThread().getName() + ": " + nextTicketNumber);
                try {
                    MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.printf("소요시간: %d ms\n", endTime - startTime);
        };

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(5);
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 3, SECONDS, blockingQueue);

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(task);
        }

        threadPoolExecutor.awaitTermination(5, SECONDS);
        threadPoolExecutor.shutdown();

    }

}
