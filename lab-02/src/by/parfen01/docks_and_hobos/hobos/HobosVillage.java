package by.parfen01.docks_and_hobos.hobos;

import by.parfen01.docks_and_hobos.control.Controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;

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
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Hobos started to eat");
        for (int i = 0; i < requiredIngredientsCount.length; ++i) {
            currentIngredientsCount.set(i, currentIngredientsCount.get(i) - requiredIngredientsCount[i]);
        }
        sleep(eatingTime * 1000L);
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Hobos ended to eat");
    }

    public void start() throws InterruptedException {
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "HobosVillage started to work");
        while (Controller.getController().isWorking()) {
            int firstCook = (int) (Math.random() * hobos.size());
            int secondCook = (int) (Math.random() * hobos.size());
            if (secondCook == firstCook) {
                secondCook = (secondCook + 1) % hobos.size();
            }
            Controller.getController().getConsoleLogger().log(
                    Level.INFO, "Hobos " + hobos.get(firstCook).getName() + " is first cook");
            Controller.getController().getConsoleLogger().log(
                    Level.INFO, "Hobos " + hobos.get(secondCook).getName() + " is second cook");
            ArrayList<Thread> threads = new ArrayList<>();
            Controller.getController().getConsoleLogger().log(
                    Level.INFO, "Hobos started to steal");
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
            Controller.getController().getConsoleLogger().log(
                    Level.INFO, "All hobos returned home");
            // нужно для stop(), чтобы не есть, когда бродяги закончили красть из-за остановки системы,
            // а не потому что всё украли
            if (!Controller.getController().isWorking()) {
                break;
            }
            cookAndEat();
        }
    }
}
