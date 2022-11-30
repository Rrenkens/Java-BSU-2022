package by.DaniilDomnin.doks_and_hobos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Docks {
    Docks(long dock_capacity, long unloading_speed, ArrayList<String> cargo_names, long hobos, ArrayList<Long> ingredients_count, long eating_time, long stealing_time) {
        this.dock_capacity = dock_capacity;
        this.unloading_speed = unloading_speed;
        this.hobos = hobos;
        this.ingredients_count = ingredients_count;
        this.eating_time = eating_time;
        this.stealing_time = stealing_time;
        this.cargo_names = cargo_names;
        current_ingredients_count = new ArrayList<>();
        for (int i = 0; i < ingredients_count.size(); ++i) {
            current_ingredients_count.add(0L);
        }
        add_ingredient_mutex = new ReentrantLock();
        cargo_names.forEach(x -> {
            cargo_count.put(x, 0L);
        });

    }

    public void Unloading (Ship ship) throws InterruptedException {
        while (ship.GetCapacity() != 0) {
            Thread.sleep(1000);
            cargo_count.put(ship.GetCargoName(), Math.min(cargo_count.get(ship.GetCargoName()) + Math.min(ship.GetCapacity(), unloading_speed), dock_capacity));
            ship.SetCapacity(Math.max(0, ship.GetCapacity() - unloading_speed));
        }
    }

    public void StartStealing () {
        for (int i = 0; i < hobos - 2; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    Stealing();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }

    private void Stealing () throws InterruptedException {
        while (true) {
            Thread.sleep(stealing_time * 1000);
            add_ingredient_mutex.lock();
            int index = GetStealIndex();
            if (index == -1) {
                continue;
            }
            cargo_count.put(cargo_names.get(index), cargo_count.get(cargo_names.get(index)) - 1);
            current_ingredients_count.set(index, current_ingredients_count.get(index) + 1);
            if (CanStartEating()) {
                ClearCurrentIngredients();;
                Thread.sleep(eating_time * 1000);
            }
            add_ingredient_mutex.unlock();
        }
    }

    private int GetStealIndex () {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < cargo_names.size(); ++i) {
            if (cargo_count.get(cargo_names.get(i)) > 0) {
                indexes.add(i);
            }
        }
        if (indexes.size() == 0) {
            return -1;
        }
        return indexes.get(new Random().nextInt(0, indexes.size()));
    }

    private void ClearCurrentIngredients() {
        for (int i = 0; i < current_ingredients_count.size(); ++i) {
            current_ingredients_count.set(i, current_ingredients_count.get(i) - ingredients_count.get(i));
        }
    }

    private boolean CanStartEating () {
        for (int i = 0; i < current_ingredients_count.size(); ++i) {
            if (current_ingredients_count.get(i) < ingredients_count.get(i)) {
                return false;
            }
        }
        return true;
    }




    private final long dock_capacity;
    private final long unloading_speed;

    private final long hobos;

    private final long eating_time;

    private final long stealing_time;

    private final ArrayList<Long> current_ingredients_count;

    private final Lock add_ingredient_mutex;

    private final ArrayList<Long> ingredients_count;

    private final ArrayList<String> cargo_names;

    private final ConcurrentHashMap<String, Long> cargo_count = new ConcurrentHashMap<>();
}
