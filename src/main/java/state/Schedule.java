package state;

import model.Lesson;
import model.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class Schedule {

    public static int MAX_DEPTH;
    public static List<Hour> HOURS;
    public static List<Classroom> CLASSROOMS;
    public static List<Lesson> LESSONS;
    public static List<Teacher> TEACHERS;

    private List<Schedule> children = new ArrayList<>();
    private int priority = 0;
    private int depth;
    private int classroom;
    private int nextHour;

    private Map<Hour, HourSchedule> scheduleParts = new HashMap<>();

    public Schedule(int depth, int hour, int classroom) {
        this.depth = depth;

        this.classroom = classroom;
        this.nextHour = hour;

        if (nextHour == HOURS.size()) {
            this.nextHour = 0;
            this.classroom = (this.classroom + 1) % 9;
        }
    }

    public void generateRandom() {
        Random random = new Random();
        HOURS.forEach(hour -> {
            CLASSROOMS.forEach(classroom -> {
                List<Lesson> stream = LESSONS.stream().filter(lesson -> lesson.getaClass() == classroom.getaClass() && hoursTaught(classroom, lesson) < lesson.getHoursPerWeek()).collect(Collectors.toList());
                if (stream.size() > 0) {
                    Lesson lesson = stream.get(random.nextInt(stream.size()));

                    List<Teacher> teacherStream = TEACHERS.stream().filter(teacher -> teacher.getTeachableLessons().contains(lesson.getId()) && canTeach(hour, teacher)).collect(Collectors.toList());
                    if (teacherStream.size() > 0) {
                        Teacher teacher = teacherStream.get(random.nextInt(teacherStream.size()));

                        getSchedulePart(hour).getTeachings().put(classroom, new Teaching(lesson, teacher));
                    }
                }
            });
        });
    }

    public void generateChildren() {
        CLASSROOMS.forEach(classroom -> {
            HOURS.forEach(nextHour -> {
                HOURS.stream().filter(hour -> hour != nextHour).forEach(next -> {
                    Schedule schedule = new Schedule(depth + 1, this.nextHour + 1, this.classroom);
                    schedule.scheduleParts.putAll(scheduleParts);

                    Teaching bubble = schedule.getSchedulePart(nextHour).getTeachings().get(classroom);
                    schedule.getSchedulePart(nextHour).getTeachings().put(classroom, schedule.getSchedulePart(next).getTeachings().get(classroom));
                    schedule.getSchedulePart(next).getTeachings().put(classroom, bubble);

                    children.add(schedule);
                });
            });
        });
    }

    public HourSchedule getSchedulePart(Hour nextHour) {
        if (!scheduleParts.containsKey(nextHour)) {
            scheduleParts.put(nextHour, new HourSchedule());
        }
        return scheduleParts.get(nextHour);
    }

    public boolean canTeach(Hour finalNextHour, Teacher teacher) {
        int todayHours = (int) scheduleParts.entrySet().stream().filter(it -> it.getKey().getDay() == finalNextHour.getDay() && it.getValue().isTeaching(teacher)).count();
        int totalHours = totalTaught(teacher);

        return !(scheduleParts.containsKey(finalNextHour) && scheduleParts.get(finalNextHour).isTeaching(teacher)) && totalHours < teacher.getMaxHoursPerWeek() && todayHours < teacher.getMaxHoursPerDay();
    }

    public int totalTaught(Teacher teacher, int day) {
        return (int) scheduleParts.entrySet().stream().filter(it -> it.getKey().getDay() == day && it.getValue().isTeaching(teacher)).count();
    }

    public int totalTaught(Teacher teacher) {
        return (int) scheduleParts.values().stream().filter(it -> it.isTeaching(teacher)).count();
    }

    public int totalTaught(Classroom it) {
        return scheduleParts.values().stream().map(part -> part.isTaught(it) ? 1 : 0).reduce((a1, a2) -> a1 + a2).orElse(0);
    }

    public int hoursTaught(Classroom classroom, Lesson lesson1) {
        return scheduleParts.values().stream().map(part -> part.isTaught(classroom, lesson1) ? 1 : 0).reduce((a1, a2) -> a1 + a2).orElse(0);
    }

    public long allEmpty() {
        return scheduleParts.values().stream().map(part -> CLASSROOMS.stream().filter(cl -> part.getTeachings().get(cl) == null).count()).reduce(0L, (a, b) -> a + b);
    }

    public List<Schedule> getChildren() {
        return children;
    }

    public Map<Hour, HourSchedule> getScheduleParts() {
        return scheduleParts;
    }

    public int getDepth() {
        return depth;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Schedule: \n" + HOURS.stream().map((hour) -> hour + ": " + scheduleParts.get(hour).toString()).reduce((a1, a2) -> a1 + a2).orElse("");
    }
}
