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
        long deadline = System.currentTimeMillis() + timeout;
        parkingQueue.add(car);
        System.out.println("Машина " + car.getId() + " встала в очередь");
        while (true){
            boolean isFirst = parkingQueue.peek() == car;
            int freeSlot = findFreeSlot();

            if (isFirst && freeSlot != -1){
                parkingSlots.set(freeSlot,car);
                parkingQueue.poll();
                System.out.println("Машина " + car.getId() + " заняла место " + freeSlot);
                getState();
                return true;
            }

            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0){
                parkingQueue.remove(car);
                return false;
            }
            try {
                wait(remaining);
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
            getState();
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
