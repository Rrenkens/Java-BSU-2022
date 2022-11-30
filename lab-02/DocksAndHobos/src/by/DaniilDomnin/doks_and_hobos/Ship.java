package by.DaniilDomnin.doks_and_hobos;

public class Ship {
    Ship(long capacity, String cargo_name) {
        this.capacity = capacity;
        this.cargo_name= cargo_name;
    }

    public String GetCargoName () {
        return cargo_name;
    }

    public long GetCapacity () {
        return capacity;
    }

    public void SetCapacity (long capacity) {
        this.capacity = capacity;
    }

    private long capacity;
    private final String cargo_name;
}
