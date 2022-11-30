package by.polina_kostyukovich.docks_and_hobos.actors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dock implements Runnable {
    public Dock(int unloadingTime, int capacity, int id) {
        this.unloadingSpeed = unloadingTime;
        this.capacity = capacity;
        this.id = id;
    }

    @Override
    public void run() {
        Cargo addedCargo = new Cargo(cargoToAdd.getType(), 0);
        while (cargoToAdd.getAmount() > 0 && getTotalCargoAmount() < capacity) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int addedAmount = Math.min(
                    Math.min(capacity - getTotalCargoAmount(), cargoToAdd.getAmount()), unloadingSpeed);
            cargos.computeIfPresent(cargoToAdd.getType(), (type, count) -> count + addedAmount);
            cargos.putIfAbsent(cargoToAdd.getType(), addedAmount);
            cargoToAdd.setAmount(cargoToAdd.getAmount() - addedAmount);
            addedCargo.setAmount(addedCargo.getAmount() + addedAmount);
        }
        isFree = true;
        String strToLog = "Dock #" + id + " ended unloading " + addedCargo.getAmount() + " ones of "
                + addedCargo.getType();
        LOGGER.log(Level.INFO, strToLog);
    }

    public Map<String, Integer> getCargos() {
        return cargos;
    }

    public int getUnloadingSpeed() {
        return unloadingSpeed;
    }

    public boolean isFree() {
        return isFree && getTotalCargoAmount() < capacity;
    }

    public void setCargoToAdd(Cargo cargoToAdd) {
        if (cargoToAdd == null) {
            throw new IllegalArgumentException("Parameter cargoToAdd is null");
        }

        this.cargoToAdd = cargoToAdd;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public int getTotalCargoAmount() {
        return cargos.values().stream()
                .mapToInt(n -> n)
                .sum();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private final int id;
    private final int unloadingSpeed;
    private final int capacity;
    private final ConcurrentMap<String, Integer> cargos = new ConcurrentHashMap<>();
    private boolean isFree = true;
    private Cargo cargoToAdd;
    private static final Logger LOGGER = Logger.getLogger(Dock.class.getName());
}
