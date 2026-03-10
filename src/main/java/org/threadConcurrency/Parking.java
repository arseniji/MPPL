package org.threadConcurrency;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.nCopies;

public class Parking {
    private List<Car> parkingSlots;
    private Queue<Car> parkingQueue;
    public Parking(int capacity){
        this.parkingSlots = new ArrayList<>(nCopies(capacity,null));
        this.parkingQueue = new ArrayDeque<>();
    }
    public synchronized boolean tryEnter(Car car,long timeout){
        long deadline = System.nanoTime() + timeout * 1000000;
        parkingQueue.add(car);
        while (true){
            int freeSlot = findFreeSlot();
            if (freeSlot != -1){
                parkingSlots.set(freeSlot,car);
                parkingQueue.remove(car);
                System.out.println("Машина " + car.getId() + " заняла место " + freeSlot);
                return true;
            }
            parkingQueue.add(car);
            System.out.println("Машина " + car.getId() + " встала в очередь");
            long remaining = deadline - System.nanoTime();
            if (remaining <= 0){
                System.out.println("Машина " + car.getId() + " не дождалась");
                parkingQueue.remove(car);
                return false;
            }
            try {
                wait(remaining / 1_000_000, (int) (remaining % 1_000_000));
            } catch (InterruptedException e) {
                parkingQueue.remove(car);
                return false;
            }
        }
    }
    public synchronized void exit(Car car) {
        IntStream.range(0,parkingSlots.size()).filter(i -> parkingSlots.get(i) == car).findFirst().ifPresent(index ->{
            parkingSlots.set(index,null);
            System.out.println("Машина " + car.getId() + " освободила место №" + index);
            notifyAll();
        });
    }

    private Integer findFreeSlot() {
        return IntStream.range(0, parkingSlots.size()).filter(i -> parkingSlots.get(i) == null).findFirst().orElse(-1);
    }

    public void getState(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parkingSlots.size();i++){
            sb.append("|").append(parkingSlots.get(i) == null ? ("\uD83C\uDD7F\uFE0F") : ("\uD83D\uDE98")).append("|");
        }
        System.out.println("Парковка: " + sb);
    }
}
