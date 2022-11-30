package by.polina_kostyukovich.docks_and_hobos;

import by.polina_kostyukovich.docks_and_hobos.actors.*;
import by.polina_kostyukovich.docks_and_hobos.controllers.HoboController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Model {
    public void readData(String filename) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject info = (JSONObject) parser.parse(new FileReader(filename));

        final int maxShipsInTunnel = (int) (long) info.get("Max ships in tunnel");
        tunnel = new Tunnel(maxShipsInTunnel);

        final int generatingTime = (int) (long) info.get("Generating time");
        final int minShipCapacity = (int) (long) info.get("Min ship capacity");
        final int maxShipCapacity = (int) (long) info.get("Max ship capacity");
        Object[] cargoTypesJson = ((JSONArray) info.get("Cargo types")).toArray();
        ArrayList<String> cargoTypes = new ArrayList<>(cargoTypesJson.length);
        for (Object type : cargoTypesJson) {
            cargoTypes.add((String) type);
        }
        shipGenerator = new ShipGenerator(generatingTime, cargoTypes, minShipCapacity, maxShipCapacity, tunnel);

        final int numberOfDocks = (int) (long) info.get("Number of docks");
        final int unloadingSpeed = (int) (long) info.get("Unloading speed");
        final int dockCapacity = (int) (long) info.get("Dock capacity");
        docks = new ArrayList<>(numberOfDocks);
        for (int i = 0; i < numberOfDocks; ++i) {
            docks.add(new Dock(unloadingSpeed, dockCapacity, i));
        }

        final int numberOfHobos = (int) (long) info.get("Number of hobos");
        Object[] ingredientsCountJson = ((JSONArray) info.get("Ingredients count")).toArray();
        Map<String, Integer> ingredientsCount = new HashMap<>(ingredientsCountJson.length);
        for (Object ingredientCount : ingredientsCountJson) {
            JSONObject ingredientCountJson = (JSONObject) ingredientCount;
            ingredientsCount.put((String) ingredientCountJson.get("Cargo type"),
                    (int) (long) ingredientCountJson.get("Cargo amount"));
        }
        final int stealingTime = (int) (long) info.get("Stealing time");
        final int eatingTime = (int) (long) info.get("Eating time");
        ArrayList<Hobo> hobos = new ArrayList<>(numberOfHobos);
        for (int i = 0; i < numberOfHobos; ++i) {
            hobos.add(new Hobo(stealingTime, i));
        }
        hoboController = new HoboController(hobos, ingredientsCount, docks, eatingTime, cargoTypes);
    }

    public ShipGenerator getShipGenerator() {
        return shipGenerator;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public ArrayList<Dock> getDocks() {
        return docks;
    }

    public HoboController getHoboController() {
        return hoboController;
    }

    public Dock getFreeDock() {
        return docks.stream()
                .filter(Dock::isFree)
                .findAny().orElse(null);
    }

    public boolean hasFreeDock() {
        return docks.stream()
                .anyMatch(Dock::isFree);
    }

    private ShipGenerator shipGenerator;
    private Tunnel tunnel;
    private ArrayList<Dock> docks;
    private HoboController hoboController;
}
