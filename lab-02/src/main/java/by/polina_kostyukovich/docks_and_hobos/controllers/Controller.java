package by.polina_kostyukovich.docks_and_hobos.controllers;

import by.polina_kostyukovich.docks_and_hobos.Model;
import by.polina_kostyukovich.docks_and_hobos.actors.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Controller {
    public Controller(String filenameForReadingData) throws IOException, ParseException {
        this.model = new Model();
        model.readData(filenameForReadingData);
    }

    public void startRunning() {
        LoggersController loggersController = new LoggersController();
        loggersController.configureLoggers();

        new Thread(loggersController).start();
        new Thread(model.getShipGenerator()).start();
        new Thread(model.getHoboController()).start();

        while (true) {
            while (model.hasFreeDock() && model.getTunnel().hasShips()) {
                Ship nextShip = model.getTunnel().takeShip();
                Dock freeDock = model.getFreeDock();
                freeDock.setFree(false);
                freeDock.setCargoToAdd(nextShip.getCargo());
                new Thread(freeDock).start();
            }
        }
    }

    private final Model model;
}
