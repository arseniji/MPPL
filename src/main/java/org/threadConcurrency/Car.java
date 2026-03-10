package org.threadConcurrency;

import java.util.Random;

public class Car implements Runnable{
    private static int counter = 1;
    private final int id;
    private final Parking parking;
    private final Random random = new Random();

    public Car(Parking parking){
        this.id = counter++;
        this.parking = parking;

    }

    public int getId(){
        return id;
    }

    @Override
    public void run(){
        System.out.println("Машина " + id + " подъехала к парковке");
        if (parking.tryEnter(this, 5000)) {
            try {
                Thread.sleep(random.nextInt(6000) + 2000);
            } catch (InterruptedException e) {}
            parking.exit(this);
        } else {
            System.out.println("Машина " + id + " не дождалась");
        }
    }
}
