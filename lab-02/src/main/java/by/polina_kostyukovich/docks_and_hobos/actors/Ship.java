package by.polina_kostyukovich.docks_and_hobos.actors;

public class Ship {
    private final Cargo cargo;

    public Ship(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("Parameter cargo is null");
        }

        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }
}
