package state;

import model.Lesson;
import model.Teacher;

public class Teaching {

    private Lesson lesson;
    private Teacher teacher;

    public Teaching(Lesson lesson, Teacher teacher) {
        this.lesson = lesson;
        this.teacher = teacher;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teaching teaching = (Teaching) o;

        if (lesson != null ? !lesson.equals(teaching.lesson) : teaching.lesson != null) return false;
        return teacher != null ? teacher.equals(teaching.teacher) : teaching.teacher == null;
    }

    @Override
    public int hashCode() {
        int result = lesson != null ? lesson.hashCode() : 0;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        return result;
    }
}
