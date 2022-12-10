package by.parfen01.docks_and_hobos;

import by.parfen01.docks_and_hobos.control.Controller;
import by.parfen01.docks_and_hobos.ships.Ship;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

public class Dock {
    private final int id;
    private final int unloadingSpeed;
    private final int[]  maxProductCapacity;
    private final AtomicIntegerArray productsCount;

    public Dock(int id, int unloadingSpeed, int[] maxProductCapacity) {
        this.id = id;
        this.unloadingSpeed = unloadingSpeed;
        this.maxProductCapacity = maxProductCapacity;
        productsCount = new AtomicIntegerArray(maxProductCapacity.length);
    }

    public void UnloadShip(Ship ship) throws InterruptedException {
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Ship number " + ship.getId() + " started to unload in dock number " + id);
        int product = Controller.getController().getCargoDecoder().cargoToProduct(ship.getCargoType());
        int pred = productsCount.get(product);
        productsCount.set(product, Math.min(maxProductCapacity[product],
                productsCount.get(product) + ship.getShipCapacity()));
        sleep((productsCount.get(product) - pred) * 1000L / unloadingSpeed);
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Ship number " + ship.getId() + " unloaded in dock number " + id);
    }

    public void start() throws InterruptedException {
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Dock number " + id + " started to work");
        while (Controller.getController().isWorking()) {
            UnloadShip(Controller.getController().getTunnel().getShip());
        }
    }

    public boolean stealProduct(int product) {
        if (productsCount.get(product) == 0) {
            return false;
        }
        productsCount.decrementAndGet(product);
        return true;
    }

    public int getId() {
        return id;
    }
}
