package by.parfen01.docks_and_hobos;

import java.util.PriorityQueue;

public class Tunnel {
    private final PriorityQueue<Ship> shipQueue;
    private final int maxShipCount;

    public Tunnel(int maxShipCount) {
        this.maxShipCount = maxShipCount;
        this.shipQueue = new PriorityQueue<>();
    }

    public void addShip(Ship ship) {
        if (shipQueue.size() == maxShipCount) {
            return;
        }
        shipQueue.add(ship);
        notify();
    }

    public Ship getShip() throws InterruptedException {
        while (shipQueue.isEmpty()) {
            wait();
        }
        return shipQueue.poll();
    }
}
