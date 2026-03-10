package org.threadConcurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static ExecutorService executorPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        Parking parking = new Parking(5);
        CarGenerator generator = new CarGenerator(20, parking);
        Thread generatorThread = new Thread(generator);
        generatorThread.start();
        try {
            generatorThread.join();
        }
        catch (InterruptedException e) {}
        executorPool.shutdown();
        try {
            if (!executorPool.awaitTermination(60, TimeUnit.SECONDS)) {
                executorPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorPool.shutdownNow();
        }

        System.out.println("Симуляция завершена.");
    }
}