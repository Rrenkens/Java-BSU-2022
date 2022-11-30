package by.DaniilDomnin.doks_and_hobos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        Object obj = new JSONParser().parse(new FileReader(args[0]));
        JSONObject jo = (JSONObject) obj;
        ShipGenerator generator = new ShipGenerator((long)jo.get("ship_capacity_max"), (long)jo.get("ship_capacity_min"), (ArrayList<String>) jo.get("cargo_types"));
        Docks docks = new Docks((long)jo.get("dock_capacity"), (long)jo.get("unloading_speed"),  (ArrayList<String>) jo.get("cargo_types"), (long)jo.get("hobos"),   (ArrayList<Long>) jo.get("ingredients_count"), (long)jo.get("eating_time"), (long)jo.get("stealing_time"));
        Controller c = new Controller(generator, docks, (long)jo.get("generating_time"), (long)jo.get("max_ships"));
    }
}