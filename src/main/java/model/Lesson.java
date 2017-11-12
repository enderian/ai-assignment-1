package model;

public class Lesson implements Cloneable {

    private int id;
    private String name;

    private Class aClass;
    private int hoursPerWeek;

    public Lesson(int id, String name, Class aClass, int hoursPerWeek) {
        this.id = id;
        this.name = name;
        this.aClass = aClass;
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Class getaClass() {
        return aClass;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;
        return id == lesson.id;
    }
}
