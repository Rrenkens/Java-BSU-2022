package by.DaniilDomnin.doks_and_hobos;

import java.util.ArrayList;
import java.util.Random;

public class ShipGenerator {

    ShipGenerator(long ship_capacity_max, long ship_capacity_min, ArrayList<String> cargo_names) {
        this.ship_capacity_max = ship_capacity_max;
        this.ship_capacity_min = ship_capacity_min;
        this.cargo_names = cargo_names;
    }

    public Ship GenerateShip() {
        Random ran = new Random();
        return new Ship(ran.nextLong(ship_capacity_min, ship_capacity_max + 1), cargo_names.get(ran.nextInt(0, cargo_names.size())));
    }

    private long ship_capacity_max;
    private long ship_capacity_min;

    private ArrayList<String> cargo_names;
}
