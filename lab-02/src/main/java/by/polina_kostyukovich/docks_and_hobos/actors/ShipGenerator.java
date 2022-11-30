package by.polina_kostyukovich.docks_and_hobos.actors;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipGenerator implements Runnable {
    public ShipGenerator(int generatingTime, ArrayList<String> cargoTypes, int minShipCapacity, int maxShipCapacity,
                         Tunnel tunnel) {
        if (cargoTypes == null || tunnel == null) {
            throw new IllegalArgumentException("Some parameter in ShipGenerator constructor is null");
        }

        this.generatingTime = generatingTime;
        this.cargoTypes = cargoTypes;
        this.minShipCapacity = minShipCapacity;
        this.maxShipCapacity = maxShipCapacity;
        this.tunnel = tunnel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000L * generatingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Ship newShip = generateShip();
            String strToLog;
            if (tunnel.addShip(newShip)) {
                strToLog = "Added ship: type - " + newShip.getCargo().getType() + ", amount - "
                        + newShip.getCargo().getAmount();
            } else {
                strToLog = "Killed ship :(";
            }
            LOGGER.log(Level.INFO, strToLog);
        }
    }

    private Ship generateShip() {
        int capacity = (int) (Math.random() * (maxShipCapacity - minShipCapacity + 1) + minShipCapacity);
        String type = cargoTypes.get((new Random()).nextInt(cargoTypes.size()));
        return new Ship(new Cargo(type, capacity));
    }

    public int getGeneratingTime() {
        return generatingTime;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private final int generatingTime;
    private final ArrayList<String> cargoTypes;
    private final int minShipCapacity;
    private final int maxShipCapacity;
    private final Tunnel tunnel;
    private static final Logger LOGGER = Logger.getLogger(ShipGenerator.class.getName());
}
