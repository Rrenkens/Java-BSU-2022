package by.parfen01.docks_and_hobos.hobos;

import by.parfen01.docks_and_hobos.control.Controller;
import by.parfen01.docks_and_hobos.Dock;

import java.util.ArrayList;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

public class Hobo {
    private final int stealingTime;
    private final String name;

    public Hobo(int stealingTime, String name) {
        this.stealingTime = stealingTime;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void start() throws InterruptedException {
        while (Controller.getController().isWorking()) {
            int product = Controller.getController().getHobosVillage().getAbsentProduct();
            if (product == -1) {
                break;
            }
            steal(product);
        }
    }

    private void steal(int product) throws InterruptedException {
        int dockId = -1;
        label:
        while (Controller.getController().isWorking()) {
            ArrayList<Dock> docks = Controller.getController().getDocks();
            for (Dock dock : docks) {
                if (dock.stealProduct(product)) {
                    dockId = dock.getId();
                    break label;
                }
            }
        }
        // нужно для stop(). Если ничего не нашёл, при остановке, то просто возвращается домой, иначе честно доносит до дома
        if (dockId == -1 && !Controller.getController().isWorking()) {
            return;
        }
        sleep(stealingTime * 1000L);
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "Hobo " + name + " stole  " +
                        Controller.getController().getCargoDecoder().getProductName(product) +
                        " from dock number " + dockId);
        Controller.getController().getHobosVillage()
                .getCurrentIngredientsCount().incrementAndGet(product);
    }
}
