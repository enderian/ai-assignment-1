package state;

import model.Lesson;
import model.Teacher;

public class Teaching implements Cloneable {

    public Hour hour;
    public Lesson lesson;
    public Teacher teacher;

    public Teaching(Hour hour, Lesson lesson, Teacher teacher) {
        this.hour = hour;
        this.lesson = lesson;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return lesson.getName() + " (" + teacher.getName() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teaching teaching = (Teaching) o;
        if (!lesson.equals(teaching.lesson)) return false;
        return teacher.equals(teaching.teacher);
    }

    @Override
    public int hashCode() {
        int result = hour.hashCode();
        result = 31 * result + lesson.hashCode();
        result = 31 * result + teacher.hashCode();
        return result;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
