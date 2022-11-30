package by.polina_kostyukovich.docks_and_hobos.actors;

import by.polina_kostyukovich.docks_and_hobos.controllers.HoboController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Hobo implements Runnable {
    public Hobo(int stealingTime, int id) {
        this.stealingTime = stealingTime;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(stealingTime * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cargoToBeStolen.getDock().getCargos().compute(cargoToBeStolen.getCargoType(), (type, count) -> count - 1);
        cargoToBeStolen.decrementStealingHobosNumber();
        LOGGER.log(Level.FINE, "Hobo #" + id + " stole one " + cargoToBeStolen.getCargoType());
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setCargoToBeStolen(HoboController.CargoToBeStolen cargoToBeStolen) {
        if (cargoToBeStolen == null) {
            throw new IllegalArgumentException("cargoToBeStolen is null");
        }

        this.cargoToBeStolen = cargoToBeStolen;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private final int id;
    private final int stealingTime;
    private boolean isFree = true;
    private HoboController.CargoToBeStolen cargoToBeStolen;
    private static final Logger LOGGER = Logger.getLogger(Hobo.class.getName());
}
