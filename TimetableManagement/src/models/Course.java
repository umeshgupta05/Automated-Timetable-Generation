package models;

public class Course {
    private int id;
    private String name;
    private String code;
    private int capacity;
    private boolean requiresLab;

    public Course(int id, String name, String code, int capacity, boolean requiresLab) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.capacity = capacity;
        this.requiresLab = requiresLab;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean requiresLab() {
        return requiresLab;
    }
}
