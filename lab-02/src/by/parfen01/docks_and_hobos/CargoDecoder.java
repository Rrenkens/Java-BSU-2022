package by.parfen01.docks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class CargoDecoder {
    private final HashMap<String, Integer> cargoRepresent;

    public CargoDecoder(ArrayList<String> cargo) {
        cargoRepresent = new HashMap<>();
        IntStream.range(0, cargo.size()).forEach(i -> cargoRepresent.put(cargo.get(i), i));
    }

    public int cargoToProduct(String cargo) {
        return cargoRepresent.get(cargo);
    }
}
