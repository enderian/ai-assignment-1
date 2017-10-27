package model;

import java.util.List;

public class Teacher {

    private int id;
    private String name;

    private List<Integer> teachableLessons;
    private int maxHoursPerDay, maxHoursPerWeek;

    public Teacher(int id, String name, List<Integer> teachableLessons, int maxHoursPerDay, int maxHoursPerWeek) {
        this.id = id;
        this.name = name;
        this.teachableLessons = teachableLessons;
        this.maxHoursPerDay = maxHoursPerDay;
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getTeachableLessons() {
        return teachableLessons;
    }

    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }

    public int getMaxHoursPerWeek() {
        return maxHoursPerWeek;
    }
}
