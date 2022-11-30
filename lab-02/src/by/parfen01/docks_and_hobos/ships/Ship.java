package by.parfen01.docks_and_hobos.ships;

import by.parfen01.docks_and_hobos.control.Controller;

import java.util.logging.Level;

public class Ship {
    private final int id;
    private final int shipCapacity;
    private final String cargoType;
    static private int lastId = 0;

    public Ship(int shipCapacity, String cargoType) {
        this.id = ++lastId;
        this.shipCapacity = shipCapacity;
        this.cargoType = cargoType;
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Created new ship");
    }

    public int getShipCapacity() {
        return shipCapacity;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getId() {
        return id;
    }
}
