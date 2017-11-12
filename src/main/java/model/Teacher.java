/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
package model;

import java.util.List;

public class Teacher implements Cloneable {

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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id;
    }
}
