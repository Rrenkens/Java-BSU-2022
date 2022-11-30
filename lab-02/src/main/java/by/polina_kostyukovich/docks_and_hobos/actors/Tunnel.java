package by.polina_kostyukovich.docks_and_hobos.actors;

import java.util.concurrent.LinkedBlockingQueue;

public class Tunnel {
    private final LinkedBlockingQueue<Ship> ships;

    public Tunnel(int maxShipsNumber) {
        ships = new LinkedBlockingQueue<>(maxShipsNumber);
    }

    public Ship takeShip() {
        return ships.remove();
    }

    public boolean addShip(Ship ship) {
        return ships.offer(ship);
    }

    public boolean hasShips() {
        return !ships.isEmpty();
    }
}
