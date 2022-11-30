package by.polina_kostyukovich.docks_and_hobos.actors;

public class Cargo {
    private final String type;
    private int amount;

    public Cargo(String type, int amount) {
        if (type == null) {
            throw new IllegalArgumentException("Parameter type is null");
        }

        this.type = type;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}
