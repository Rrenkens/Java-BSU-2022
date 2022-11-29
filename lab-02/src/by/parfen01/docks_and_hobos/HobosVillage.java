package by.parfen01.docks_and_hobos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Thread.sleep;

public class HobosVillage {
    private final int[] requiredIngredientsCount;
    private final AtomicIntegerArray currentIngredientsCount;
    private final int eatingTime;
    private final ArrayList<Hobos> hobos;

    public HobosVillage(int[] requiredIngredientsCount, int eatingTime, ArrayList<Hobos> hobos) {
        this.requiredIngredientsCount = requiredIngredientsCount;
        this.eatingTime = eatingTime;
        this.hobos = hobos;
        currentIngredientsCount = new AtomicIntegerArray(this.requiredIngredientsCount.length);
    }

    public AtomicIntegerArray getCurrentIngredientsCount() {
        return currentIngredientsCount;
    }

    public int getAbsentProduct() {
        for (int i = 0; i < requiredIngredientsCount.length; ++i) {
            if (requiredIngredientsCount[i] > currentIngredientsCount.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void cookAndEat() throws InterruptedException {
        for (int i = 0; i < requiredIngredientsCount.length; ++i) {
            currentIngredientsCount.set(i, currentIngredientsCount.get(i) - requiredIngredientsCount[i]);
        }
        sleep(eatingTime * 1000L);
    }

    public void start() throws InterruptedException {
        while (Controller.getController().isWorking()) {
            int firstCook = (int) (Math.random() * hobos.size());
            int secondCook = (int) (Math.random() * hobos.size());
            if (secondCook == firstCook) {
                secondCook = (secondCook + 1) % hobos.size();
            }
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < hobos.size(); ++i) {
                if (i != firstCook && i != secondCook) {
                    int finalI = i;
                    Thread thread = new Thread(() -> {
                        try {
                            hobos.get(finalI).start();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    thread.start();
                    threads.add(thread);
                }
            }
            for (Thread i : threads) {
                i.join();
            }
            cookAndEat();
        }
    }
}
