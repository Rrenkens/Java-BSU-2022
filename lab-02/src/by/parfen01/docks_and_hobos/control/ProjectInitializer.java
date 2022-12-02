package by.parfen01.docks_and_hobos.control;

import by.parfen01.docks_and_hobos.CargoDecoder;
import by.parfen01.docks_and_hobos.Dock;
import by.parfen01.docks_and_hobos.Tunnel;
import by.parfen01.docks_and_hobos.hobos.Hobo;
import by.parfen01.docks_and_hobos.hobos.HobosVillage;
import by.parfen01.docks_and_hobos.ships.ShipGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectInitializer {
    private static String previousPath;
    public static Controller initController(String path) throws IOException, ParseException {
        if (path.equals(previousPath)) {
            return Controller.getController();
        }
        previousPath = path;
        ArrayList<Hobo> hobos = new ArrayList<>();
        ArrayList<String> cargo = new ArrayList<>();
        ArrayList<Dock> docks = new ArrayList<>();
        ShipGenerator shipGenerator;
        HobosVillage hobosVillage;
        CargoDecoder cargoDecoder;
        Tunnel tunnel;
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jsonObject = (JSONObject) obj;
        tunnel = new Tunnel((int) (long) jsonObject.get("Tunnel"));
        JSONObject jsonShip = (JSONObject) jsonObject.get("ShipGenerator");
        int shipCapacityMin = (int) (long) jsonShip.get("shipCapacityMin");
        int shipCapacityMax = (int) (long) jsonShip.get("shipCapacityMax");
        int generatingTime = (int) (long) jsonShip.get("generatingTime");
        JSONArray jsonCargo = (JSONArray) jsonShip.get("cargoTypes");
        for (Object i : jsonCargo) {
            cargo.add((String) i);
        }
        shipGenerator = new ShipGenerator(shipCapacityMin, shipCapacityMax, generatingTime, cargo);
        cargoDecoder = new CargoDecoder(cargo);
        JSONArray jsonDocks = (JSONArray) jsonObject.get("Docks");
        for (Object i : jsonDocks) {
           JSONObject jsonDock = (JSONObject) i;
           int id = (int) (long) jsonDock.get("id");
           int unloadingSpeed = (int) (long) jsonDock.get("unloadingSpeed");
           int[] maxProductCapacity = new int[cargo.size()];
           JSONArray jsonCapacity = (JSONArray) jsonDock.get("maxProductCapacity");
           for (int j = 0; j < jsonCapacity.size(); ++j) {
               maxProductCapacity[j] = (int) (long) jsonCapacity.get(j);
           }
           docks.add(new Dock(id, unloadingSpeed, maxProductCapacity));
        }
        JSONArray jsonHobos = (JSONArray) jsonObject.get("Hobos");
        for (Object i : jsonHobos) {
            JSONObject jsonHobo = (JSONObject) i;
            int stealingTime = (int) (long) jsonHobo.get("stealingTime");
            String name = (String) jsonHobo.get("name");
            hobos.add(new Hobo(stealingTime, name));
        }
        JSONObject jsonVillage = (JSONObject) jsonObject.get("hobosVillage");
        int eatingTime = (int) (long) jsonVillage.get("eatingTime");
        JSONArray jsonIngredients = (JSONArray) jsonVillage.get("ingredientsCount");
        int[] requiredIngredientsCount = new int[cargo.size()];
        for (int i = 0; i < jsonIngredients.size(); ++i) {
            requiredIngredientsCount[i] = (int) (long) jsonIngredients.get(i);
        }
        hobosVillage = new HobosVillage(requiredIngredientsCount, eatingTime, hobos);
        return new Controller(shipGenerator, hobosVillage, tunnel, cargoDecoder, docks);
    }
}
