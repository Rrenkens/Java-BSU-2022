package by.polina_kostyukovich.docks_and_hobos.controllers;

import by.polina_kostyukovich.docks_and_hobos.actors.Dock;
import by.polina_kostyukovich.docks_and_hobos.actors.Hobo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoboController implements Runnable {
    public HoboController(ArrayList<Hobo> hobos, Map<String, Integer> ingredientsCount, List<Dock> docks,
                          int eatingTime, List<String> allCargoTypes) {
        if (hobos == null || ingredientsCount == null || docks == null || allCargoTypes == null) {
            throw new IllegalArgumentException("Some parameter in HoboController constructor is null");
        }
        this.hobos = hobos;
        hobos.remove(hobos.size() - 1);
        hobos.remove(hobos.size() - 1);
        this.ingredientsCount = ingredientsCount;
        this.eatingTime = eatingTime;

        cargos = new ArrayList<>();
        for (String cargoType : allCargoTypes) {
            for (Dock dock : docks) {
                cargos.add(new CargoToBeStolen(dock, cargoType));
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            goStealingAndEating();
        }
    }

    private void goStealingAndEating() {
        while (areAllDocksEmpty()) {}

        Map<String, Integer> curIngrCount = new HashMap<>(ingredientsCount.size());
        curIngrCount.putAll(ingredientsCount);
        checkStock(curIngrCount);

        while (containsNotOnlyZeros(curIngrCount)) {
            while (hasFreeHobo()) {
                tryHoboToStealCargo(curIngrCount);
            }
        }

        while (!areAllHobosFree()) {}
        try {
            LOGGER.log(Level.INFO, "Hobos started eating");
            Thread.sleep(eatingTime * 1000L);
            LOGGER.log(Level.INFO, "Hobos finished eating");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryHoboToStealCargo(Map<String, Integer> curIngrCount) {
        while (areAllDocksEmpty()) {
            if (!containsNotOnlyZeros(curIngrCount)) return;
        }

        List<String> neededCargoTypes = curIngrCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();

        boolean wasStealingDetected = false;
        for (String cargoType : neededCargoTypes) {
            Optional<CargoToBeStolen> availableCargo = findAvailableCertainCargo(cargoType);
            if (availableCargo.isEmpty()) continue;

            stealCargo(availableCargo.get());
            curIngrCount.compute(cargoType, (type, count) -> count - 1);

            wasStealingDetected = true;
            break;
        }

        if (!wasStealingDetected) {
            Optional<String> stolenCargoType = tryToStealRandomCargo();
            if (stolenCargoType.isEmpty()) return;
            replaceRandomCargoInRecipe(curIngrCount, stolenCargoType.get());
        }
    }

    private Optional<CargoToBeStolen> findAvailableCertainCargo(String cargoType) {
        return cargos.stream()
                .filter(cargo -> cargo.getCargoType().equals(cargoType))
                .filter(cargo -> cargo.getDock().getCargos().containsKey(cargo.getCargoType())
                        && cargo.getDock().getCargos().get(cargo.getCargoType()) != null)
                .filter(cargo -> cargo.getStealingHobosNumber()
                        < cargo.getDock().getCargos().get(cargoType))
                .findAny();
    }

    private Optional<String> tryToStealRandomCargo() {
        Optional<CargoToBeStolen> stolenCargo = cargos.stream()
                .filter(cargo -> cargo.getDock().getCargos().containsKey(cargo.getCargoType())
                        && cargo.getDock().getCargos().get(cargo.getCargoType()) != null
                        && cargo.getDock().getCargos().get(cargo.getCargoType()) > cargo.getStealingHobosNumber())
                .findAny();
        if (stolenCargo.isEmpty()) {
            return Optional.empty();
        }
        stealCargo(stolenCargo.get());
        return Optional.of(stolenCargo.get().getCargoType());
    }

    private void stealCargo(CargoToBeStolen cargo) {
        Hobo freeHobo = getFreeHobo();
        freeHobo.setCargoToBeStolen(cargo);
        cargo.incrementStealingHobosNumber();
        freeHobo.setFree(false);
        new Thread(freeHobo).start();
    }

    private void replaceRandomCargoInRecipe(Map<String, Integer> curIngrCount, String stolenCargoType) {
        Optional<String> randomCargoType = curIngrCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .findAny();
        if (randomCargoType.isPresent()) {
            curIngrCount.compute(randomCargoType.get(), (type, count) -> count - 1);
        } else {
            stock.computeIfPresent(stolenCargoType, (type, count) -> count + 1);
            stock.putIfAbsent(stolenCargoType, 1);
        }
    }

    private boolean areAllDocksEmpty() {
        return cargos.stream()
                .filter(cargo -> cargo.getDock().getCargos().containsKey(cargo.getCargoType())
                        && cargo.getDock().getCargos().get(cargo.getCargoType()) != null)
                .noneMatch(cargo ->
                        cargo.getDock().getCargos().get(cargo.getCargoType()) > cargo.getStealingHobosNumber());
    }

    private void checkStock(Map<String, Integer> curIngrCount) {
        stock.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue() > 0)
                .filter(entry -> curIngrCount.containsKey(entry.getKey()) && curIngrCount.get(entry.getKey()) != null)
                .forEach(entry -> {
                    int amountToTakeFromStock = Math.min(entry.getValue(), curIngrCount.get(entry.getKey()));
                    stock.compute(entry.getKey(), (type, count) -> count - amountToTakeFromStock);
                    curIngrCount.compute(entry.getKey(), (type, count) -> count - amountToTakeFromStock);
                    String strToLog = amountToTakeFromStock + " ones of " + entry.getKey() + " were taken from stock";
                    LOGGER.log(Level.FINE, strToLog);
                });
    }

    private boolean containsNotOnlyZeros(Map<String, Integer> map) {
        return map.values().stream()
                .mapToInt(n -> n)
                .anyMatch(n -> n != 0);
    }

    private boolean hasFreeHobo() {
        return hobos.stream()
                .anyMatch(Hobo::isFree);
    }

    private Hobo getFreeHobo() {
        return hobos.stream()
                .filter(Hobo::isFree)
                .findAny().orElse(null);
    }

    private boolean areAllHobosFree() {
        return hobos.stream()
                .allMatch(Hobo::isFree);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private final ArrayList<Hobo> hobos;
    private final Map<String, Integer> ingredientsCount;
    private final int eatingTime;
    private final Map<String, Integer> stock = new HashMap<>();
    private final ArrayList<CargoToBeStolen> cargos;
    private static final Logger LOGGER = Logger.getLogger(HoboController.class.getName());

    public static class CargoToBeStolen {
        private final Dock dock;
        private final String cargoType;
        private final AtomicInteger stealingHobosNumber = new AtomicInteger(0);

        public CargoToBeStolen(Dock dock, String cargoType) {
            if (dock == null || cargoType == null) {
                throw new IllegalArgumentException("Some parameter in CargoToBeStolen constructor is null");
            }

            this.dock = dock;
            this.cargoType = cargoType;
        }

        public Dock getDock() {
            return dock;
        }

        public String getCargoType() {
            return cargoType;
        }

        public void incrementStealingHobosNumber() {
            stealingHobosNumber.incrementAndGet();
        }

        public void decrementStealingHobosNumber() {
            stealingHobosNumber.decrementAndGet();
        }

        public int getStealingHobosNumber() {
            return stealingHobosNumber.get();
        }
    }
}
