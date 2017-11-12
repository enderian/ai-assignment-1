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

    private int depth;

    public Teaching[][] teachings;
    public int cost = 0;
    public int distance = 0;
    public int priority = 0;

    public Schedule(int depth) {
        this.depth = depth;
        this.teachings = new Teaching[CLASSROOMS.size()][HOURS.size()];
    }

    public Schedule(int depth, Teaching[][] teachings) {
        this.depth = depth;
        this.teachings = new Teaching[CLASSROOMS.size()][HOURS.size()];

        for (int i = 0; i < teachings.length; i++) {
            for (int j = 0; j < teachings[i].length; j++) {
                this.teachings[i][j] = teachings[i][j] == null ? null : (Teaching) teachings[i][j].clone();
            }
        }
    }

    public void generateRandom() {
        Random random = new Random();
        HOURS.forEach(hour -> {
            final int hourIndex = HOURS.indexOf(hour);
            CLASSROOMS.forEach(classroom -> {
                final int classroomIndex = CLASSROOMS.indexOf(classroom);
                List<Lesson> stream = LESSONS.stream().filter(lesson -> lesson.getaClass() == classroom.getaClass() && hoursTaught(classroom, lesson) < lesson.getHoursPerWeek()).collect(Collectors.toList());
                if (stream.size() > 0) {
                    Lesson lesson = stream.get(random.nextInt(stream.size()));

                    List<Teacher> teacherStream = TEACHERS.stream().filter(teacher -> teacher.getTeachableLessons().contains(lesson.getId()) && canTeach(hour, teacher)).collect(Collectors.toList());
                    if (teacherStream.size() > 0) {
                        Teacher teacher = teacherStream.get(random.nextInt(teacherStream.size()));

                        teachings[classroomIndex][hourIndex] = new Teaching(hour, lesson, teacher);
                    }
                } else {
                    teachings[classroomIndex][hourIndex] = null;
                }
            });
        });
    }

    public List<Schedule> generateChildren() {
        List<Schedule> children = new ArrayList<>();
        for (Classroom classroom : CLASSROOMS) {
            int classroomIndex = CLASSROOMS.indexOf(classroom);

            for (int hour = 1; hour < HOURS.size(); hour++) {
                Schedule schedule = new Schedule(depth + 1, teachings);

                Teaching bubble1 = teachings[classroomIndex][0];
                Teaching bubble2 = teachings[classroomIndex][hour];

                schedule.teachings[classroomIndex][0] =
                        bubble2 == null ? null : new Teaching(bubble1 == null ? HOURS.get(0) : bubble1.hour, bubble2.lesson, bubble2.teacher);
                schedule.teachings[classroomIndex][hour] =
                        bubble1 == null ? null : new Teaching(bubble2 == null ? HOURS.get(hour) : bubble2.hour, bubble1.lesson, bubble1.teacher);

                children.add(schedule);
            }
        }
        return children;
    }

    public boolean canTeach(Hour finalNextHour, Teacher teacher) {
        int todayHours = Arrays.stream(teachings)
                .map(lv2 -> (int) Arrays.stream(lv2).filter(it -> it != null && it.hour.getDay() == finalNextHour.getDay() && it.teacher == teacher).count())
                .reduce(0, (a, b) -> a + b);
        int totalHours = totalTaught(teacher);

        return totalHours < teacher.getMaxHoursPerWeek() && todayHours < teacher.getMaxHoursPerDay() &&
                CLASSROOMS.stream().noneMatch(cl -> {
                    Teaching teaching = teachings[CLASSROOMS.indexOf(cl)][HOURS.indexOf(finalNextHour)];
                    return teaching != null && teaching.teacher == teacher;
                });
    }

    public int totalTaught(Teacher teacher, int day) {
        return Arrays.stream(teachings)
                .map(lv2 -> (int) Arrays.stream(lv2).filter(it -> it != null && it.hour.getDay() == day && it.teacher == teacher).count())
                .reduce(0, (a, b) -> a + b);
    }

    public int totalTaught(Teacher teacher) {
        return Arrays.stream(teachings)
                .map(lv2 -> (int) Arrays.stream(lv2).filter(it -> it != null && it.teacher == teacher).count())
                .reduce(0, (a, b) -> a + b);
    }

    public int totalTaught(Classroom it) {
        return (int) Arrays.stream(teachings[CLASSROOMS.indexOf(it)])
                .filter(Objects::nonNull).count();
    }

    public int hoursTaught(Classroom classroom, Lesson lesson1) {
        return (int) Arrays.stream(teachings[CLASSROOMS.indexOf(classroom)])
                .filter(it -> it != null && it.lesson == lesson1).count();
    }

    public long allEmpty() {
        return Arrays.stream(teachings)
                .map(lv2 -> (int) Arrays.stream(lv2).filter(Objects::isNull).count())
                .reduce(0, (a, b) -> a + b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Arrays.deepEquals(teachings, schedule.teachings);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(teachings);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Schedule:\n");
        for (int classroom = 0; classroom < CLASSROOMS.size(); classroom++) {
            result.append("\t").append(CLASSROOMS.get(classroom)).append(":\n");
            for (int hour = 0; hour < HOURS.size(); hour++) {
                result.append("\t\t").append(HOURS.get(hour)).append(": ").append(teachings[classroom][hour]).append("\n");
            }
        }
        return result.toString();
    }

    public int getDepth() {
        return depth;
    }
}
