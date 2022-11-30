package by.DaniilDomnin.doks_and_hobos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    Controller(ShipGenerator generator, Docks docks, long generating_time, long max_ships) throws InterruptedException {
        this.generator = generator;
        this.generating_time = generating_time;
        this.max_ships = max_ships;
        this.docks = docks;
        ship_count = new AtomicInteger();
        Thread thread = new Thread(docks::StartStealing);
        thread.start();
        Generating();
    }

    private void Generating() throws InterruptedException {
        while (true) {
            if (ship_count.get() < max_ships) {
                Ship ship = generator.GenerateShip();
                Thread thread = new Thread(()->{
                    try {
                        Uploading(ship);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                thread.start();
                ship_count.incrementAndGet();
            }

            Thread.sleep(generating_time * 1000);
        }
    }

    private void Uploading(Ship ship) throws InterruptedException {
        docks.Unloading(ship);
        ship_count.decrementAndGet();
    }

    private final ShipGenerator generator;

    private final AtomicInteger ship_count;
    private final long generating_time;

    private final long max_ships;

    private final Docks docks;
}
