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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (maxHoursPerDay != teacher.maxHoursPerDay) return false;
        if (maxHoursPerWeek != teacher.maxHoursPerWeek) return false;
        if (name != null ? !name.equals(teacher.name) : teacher.name != null) return false;
        return teachableLessons != null ? teachableLessons.equals(teacher.teachableLessons) : teacher.teachableLessons == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (teachableLessons != null ? teachableLessons.hashCode() : 0);
        result = 31 * result + maxHoursPerDay;
        result = 31 * result + maxHoursPerWeek;
        return result;
    }
}
