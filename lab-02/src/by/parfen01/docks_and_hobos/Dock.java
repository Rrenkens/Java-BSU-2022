package by.parfen01.docks_and_hobos;

import static java.lang.Thread.sleep;

public class Dock {
    private final int unloadingSpeed;
    private final int[] maxProductCapacity;
    private final int[] productsCount;

    public Dock(int unloadingSpeed, int[] maxProductCapacity) {
        this.unloadingSpeed = unloadingSpeed;
        this.maxProductCapacity = maxProductCapacity;
        productsCount = new int[maxProductCapacity.length];
    }

    public void UnloadShip(Ship ship) throws InterruptedException {
        int product = Controller.getController().getCargoDecoder().cargoToProduct(ship.getCargoType());
        int pred = productsCount[product];
        productsCount[product] = Math.min(maxProductCapacity[product],
                productsCount[product] + ship.getShipCapacity());
        sleep((productsCount[product] - pred) * 1000L / unloadingSpeed);
    }

    public void start() throws InterruptedException {
        while(Controller.getController().isWorking()) {
            UnloadShip(Controller.getController().getTunnel().getShip());
        }
    }
}
