package by.parfen01.docks_and_hobos.control;

import by.parfen01.docks_and_hobos.CargoDecoder;
import by.parfen01.docks_and_hobos.Dock;
import by.parfen01.docks_and_hobos.Tunnel;
import by.parfen01.docks_and_hobos.hobos.HobosVillage;
import by.parfen01.docks_and_hobos.ships.ShipGenerator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private boolean isWorking;
    private final ShipGenerator shipGenerator;
    private final HobosVillage hobosVillage;
    private final Tunnel tunnel;
    private final CargoDecoder cargoDecoder;
    private final ArrayList<Dock> docks;
    private ArrayList<Thread> workingThreads;
    private final Logger consoleLogger;
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
        this.consoleLogger = Logger.getLogger(Controller.class.getName());
        controller = this;
    }

    public static Controller getController() {
        return controller;
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
    public Logger getConsoleLogger() {
        return consoleLogger;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void start() {
        isWorking = true;
        consoleLogger.log(
                Level.INFO, "Start");
        workingThreads = new ArrayList<>();
        Thread shipGeneratorThread = new Thread(() -> {
            try {
                shipGenerator.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        workingThreads.add(shipGeneratorThread);
        Thread hobosVillageThread = new Thread(() -> {
            try {
                hobosVillage.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        workingThreads.add(hobosVillageThread);
        for (Dock i : docks) {
            Thread dockThread = new Thread(() -> {
                try {
                    i.start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            workingThreads.add(dockThread);
        }
        for (Thread i : workingThreads) {
            i.start();
        }
    }

    public void stop() throws InterruptedException {
        if (workingThreads == null) {
            return;
        }
        isWorking = false;
        for (Thread i : workingThreads) {
            if (i.getState() != Thread.State.WAITING) {
                i.join();
            }
        }
        consoleLogger.log(
                Level.INFO, "Stop");
    }
}
