package by.parfen01.docks_and_hobos;

import java.util.ArrayList;

public class Controller {
    private boolean isWorking;
    private final ShipGenerator shipGenerator;
    private final HobosVillage hobosVillage;
    private final Tunnel tunnel;
    private final CargoDecoder cargoDecoder;

    private final ArrayList<Dock> docks;
//    private final Logger logger;
    private static Controller controller;

    public Controller(ShipGenerator shipGenerator,
                      HobosVillage hobosVillage, Tunnel tunnel,
                      CargoDecoder cargoDecoder, ArrayList<Dock> docks) {
        this.isWorking = false;
        this.shipGenerator = shipGenerator;
        this.hobosVillage = hobosVillage;
        this.tunnel = tunnel;
        this.cargoDecoder = cargoDecoder;
        this.docks = docks;
        controller = this;
    }

    public static Controller getController() {
        return controller;
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    public HobosVillage getHobosVillage() {
        return hobosVillage;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public CargoDecoder getCargoDecoder() {
        return cargoDecoder;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void start() {
        isWorking = true;
    }

    public void stop() {
        isWorking = false;
    }
}
