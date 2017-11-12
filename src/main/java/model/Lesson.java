package model;

public class Lesson {

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

        if (id != lesson.id) return false;
        if (hoursPerWeek != lesson.hoursPerWeek) return false;
        if (name != null ? !name.equals(lesson.name) : lesson.name != null) return false;
        return aClass == lesson.aClass;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (aClass != null ? aClass.hashCode() : 0);
        result = 31 * result + hoursPerWeek;
        return result;
    }
}
