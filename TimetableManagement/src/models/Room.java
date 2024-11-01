package models;

public class Room {
    private int id;
    private String name;
    private int capacity;
    private boolean hasLabEquipment;

    public Room(int id, String name, int capacity, boolean hasLabEquipment) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.hasLabEquipment = hasLabEquipment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasLabEquipment() {
        return hasLabEquipment;
    }
}
