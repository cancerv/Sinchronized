package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> listOfCars = new ArrayList<>();
        listOfCars.add("Mazda");
        listOfCars.add("Toyota");
        listOfCars.add("Honda");
        listOfCars.add("Audi");
        listOfCars.add("Acura");
        listOfCars.add("Alfa Romeo");
        listOfCars.add("BMW");
        listOfCars.add("Brilliance");
        listOfCars.add("Hyundai");
        listOfCars.add("Kia");

        List<String> carsForSale = new ArrayList<>();


        final int numOfCars = 10;
        final int timeToSleepCar = 2000;
        final int numOfBuyer = 7;

        new Thread(() -> {
            for (int i = 0; i < numOfCars; i++) {
                synchronized (carsForSale) {
                    String car = listOfCars.get(i);
                    carsForSale.add(car);
                    System.out.println("Выпущенна машина модели " + car);
                    carsForSale.notify();
                }
                try {
                    Thread.sleep(timeToSleepCar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < numOfBuyer; i++) {
                synchronized (carsForSale) {
                    System.out.println("Покупатель пришел в магазин");
                    if (carsForSale.isEmpty()) {
                        System.out.println("Машин нет");
                        try {
                            carsForSale.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Покупатель купил авто модели " + carsForSale.get(0));
                    carsForSale.remove(0);
                }
            }
        }).start();
    }
}
