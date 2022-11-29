package by.parfen01.docks_and_hobos;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Thread.sleep;

public class Dock {
    private final int unloadingSpeed;
    private final int[]  maxProductCapacity;
    private final AtomicIntegerArray productsCount;

    public Dock(int unloadingSpeed, int[] maxProductCapacity) {
        this.unloadingSpeed = unloadingSpeed;
        this.maxProductCapacity = maxProductCapacity;
        productsCount = new AtomicIntegerArray(maxProductCapacity.length);
    }

    public void UnloadShip(Ship ship) throws InterruptedException {
        int product = Controller.getController().getCargoDecoder().cargoToProduct(ship.getCargoType());
        int pred = productsCount.get(product);
        productsCount.set(product, Math.min(maxProductCapacity[product],
                productsCount.get(product) + ship.getShipCapacity()));
        sleep((productsCount.get(product) - pred) * 1000L / unloadingSpeed);
    }

    public void start() throws InterruptedException {
        while(Controller.getController().isWorking()) {
            UnloadShip(Controller.getController().getTunnel().getShip());
        }
    }

    boolean stealProduct(int product) {
        if (productsCount.get(product) == 0) {
            return false;
        }
        productsCount.decrementAndGet(product);
        return true;
    }
}
