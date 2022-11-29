package by.parfen01.docks_and_hobos;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ShipGenerator {
    private final int shipCapacityMin;
    private final int shipCapacityMax;
    private final int generatingTime;
    private final ArrayList<String> cargoTypes;

    public ShipGenerator(int shipCapacityMin, int shipCapacityMax,
                         int generatingTime, ArrayList<String> cargoTypes) {
        this.shipCapacityMin = shipCapacityMin;
        this.shipCapacityMax = shipCapacityMax;
        this.generatingTime = generatingTime;
        this.cargoTypes = cargoTypes;
    }

    public void start() throws InterruptedException {
        while (Controller.getController().isWorking()) {
            Controller.getController().getTunnel().addShip(this.generate());
            sleep(generatingTime * 1000L);
        }
    }

    public Ship generate() {
        int capacity = (int) (Math.random() * (shipCapacityMax - shipCapacityMin)) + shipCapacityMin;
        String cargoType = cargoTypes.get((int) (Math.random() * cargoTypes.size()));
        return new Ship(capacity, cargoType);
    }

    public int getShipCapacityMin() {
        return shipCapacityMin;
    }

    public int getShipCapacityMax() {
        return shipCapacityMax;
    }

    public int getGeneratingTime() {
        return generatingTime;
    }

    public ArrayList<String> getCargoTypes() {
        return cargoTypes;
    }
}
