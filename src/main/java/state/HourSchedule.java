package state;

import model.Lesson;
import model.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HourSchedule {

    private Map<Classroom, Teaching> teachings = new HashMap<>();

    public Map<Classroom, Teaching> getTeachings() {
        return teachings;
    }

    public boolean isTaught(Classroom classroom, Lesson lesson) {
        return teachings.containsKey(classroom) && teachings.get(classroom) != null && teachings.get(classroom).getLesson() == lesson;
    }

    public boolean isTaught(Classroom classroom) {
        return teachings.containsKey(classroom) && teachings.get(classroom) != null;
    }

    public boolean isFull(List<Classroom> allClassrooms) {
        return teachings.size() == allClassrooms.size();
    }

    public boolean isTeaching(Teacher teacher) {
        return teachings.values().stream().anyMatch(it -> it != null && it.getTeacher() == teacher);
    }

    @Override
    public String toString() {
        return "\n" + Schedule.CLASSROOMS.stream().map(
                (clazz) -> "\t" + clazz.toString() + ":" + (teachings.get(clazz) == null ? "Nothing" : teachings.get(clazz).getLesson() + " (" + teachings.get(clazz).getTeacher() +  ")") + "\n"
        ).reduce((a1, a2) -> a1 + a2).orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HourSchedule that = (HourSchedule) o;

        return teachings != null ? teachings.equals(that.teachings) : that.teachings == null;
    }

    @Override
    public int hashCode() {
        return teachings != null ? teachings.hashCode() : 0;
    }
}
