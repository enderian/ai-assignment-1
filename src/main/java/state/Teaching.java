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
}
