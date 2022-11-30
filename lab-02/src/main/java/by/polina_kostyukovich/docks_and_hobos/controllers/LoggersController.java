package by.polina_kostyukovich.docks_and_hobos.controllers;

import by.polina_kostyukovich.docks_and_hobos.actors.Dock;
import by.polina_kostyukovich.docks_and_hobos.actors.Hobo;
import by.polina_kostyukovich.docks_and_hobos.actors.ShipGenerator;
import by.polina_kostyukovich.docks_and_hobos.utils.LogFormatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import org.apache.commons.io.FileUtils;

public class LoggersController implements Runnable {
    public LoggersController() {
        if (ShipGenerator.getLogger() == null || Dock.getLogger() == null || Hobo.getLogger() == null
            || HoboController.getLogger() == null) {
            throw new NullPointerException("Some logger is null");
        }
        loggers.add(ShipGenerator.getLogger());
        loggers.add(Dock.getLogger());
        loggers.add(Hobo.getLogger());
        loggers.add(HoboController.getLogger());

        try {
            FileUtils.cleanDirectory(new File(PATH_TO_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void configureLoggers() {
        for (Logger logger : loggers) {
            logger.setUseParentHandlers(false);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        consoleHandler.setLevel(Level.INFO);
        addHandler(consoleHandler);

        String filename = getNewFilenameAndCreateFile();
        try {
            handler = new FileHandler(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        handler.setFormatter(formatter);

        for (Logger logger : loggers) {
            logger.setLevel(Level.FINE);
        }
        addHandler(handler);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(FILE_CHANGING_INTERVAL * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String filename = getNewFilenameAndCreateFile();
            changeHandlers(filename);
        }
    }

    private synchronized void changeHandlers(String filename) {
        if (handler != null) {
            handler.flush();
        }

        for (Logger logger : loggers) {
            logger.removeHandler(handler);
        }
        if (handler != null) {
            handler.close();
        }

        try {
            handler = new FileHandler(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        handler.setFormatter(formatter);

        addHandler(handler);
    }

    private void addHandler(Handler handler) {
        for (Logger logger : loggers) {
            logger.addHandler(handler);
        }
    }

    private String getNewFilenameAndCreateFile() {
        File file;
        String filename;
        try {
            do {
                filename = PATH_TO_DIRECTORY + "/log file #" + numberOfNextFile + ".txt";
                file = new File(filename);
                ++numberOfNextFile;
            } while (!file.createNewFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    private final List<Logger> loggers = new ArrayList<>();
    private final static int FILE_CHANGING_INTERVAL = 40;
    private final LogFormatter formatter = new LogFormatter();
    private Handler handler;
    private int numberOfNextFile = 1;
    private final String PATH_TO_DIRECTORY = "src/main/java/by/polina_kostyukovich/docks_and_hobos/logs";
}
