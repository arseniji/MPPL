package org.threadConcurrency;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class CarGenerator implements Runnable{
    private final int simIter;
    private final Parking parking;

    public CarGenerator(int simIter, Parking parking){
        this.simIter = simIter;
        this.parking = parking;
        System.out.println("Поток очереди инициализирован: " + simIter + " машин");
    }

    @Override
    public void run(){
        for (int i = 0; i < simIter; i++){
            Car car = new Car(parking);
            Main.executorPool.submit(car);
            try {
                Thread.sleep(new Random().nextInt(200) + 500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
