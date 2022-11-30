package by.parfen01.docks_and_hobos;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> cargoTypes = new ArrayList<>(List.of("bread", "meat", "milk", "cucumber"));
        ShipGenerator shipGenerator = new ShipGenerator(1, 100, 10, cargoTypes);
        Hobos firstHobos = new Hobos(9);
        Hobos secondHobos = new Hobos(7);
        Hobos thirdHobos = new Hobos(14);
        Hobos fourthHobos = new Hobos(3);
        Hobos fifthHobos = new Hobos(1);
        HobosVillage village = new HobosVillage(new int[]{2, 3, 1, 4}, 30,
                new ArrayList<>(List.of(firstHobos, secondHobos, thirdHobos, fourthHobos, fifthHobos)));
        Dock firstDock = new Dock(1, 2, new int[]{100, 50, 30, 30});
        Dock secondDock = new Dock(2,4, new int[]{5, 20, 120, 20});
        Controller controller = new Controller(shipGenerator, village,
                new Tunnel(5), new CargoDecoder(cargoTypes), new ArrayList<>(List.of(firstDock, secondDock)));
        controller.start();
        sleep(500 * 1000L);
        controller.stop();
    }
}