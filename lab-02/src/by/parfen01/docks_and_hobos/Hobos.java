package by.parfen01.docks_and_hobos;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Hobos {
    private final int stealingTime;

    public Hobos(int stealingTime) {
        this.stealingTime = stealingTime;
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

    public void steal(int product) throws InterruptedException {
        label:
        while (Controller.getController().isWorking()) {
            ArrayList<Dock> docks = Controller.getController().getDocks();
            for (Dock dock : docks) {
                if (dock.stealProduct(product)) {
                    break label;
                }
            }
        }
        sleep(stealingTime * 1000L);
        Controller.getController().getHobosVillage()
                .getCurrentIngredientsCount().incrementAndGet(product);
    }
}
