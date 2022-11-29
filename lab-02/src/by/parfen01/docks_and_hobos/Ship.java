package by.parfen01.docks_and_hobos;

public class Ship {
    private final int shipCapacity;
    private final String cargoType;

    public Ship(int shipCapacity, String cargoType) {
        this.shipCapacity = shipCapacity;
        this.cargoType = cargoType;
    }

    public int getShipCapacity() {
        return shipCapacity;
    }

    public String getCargoType() {
        return cargoType;
    }
}
