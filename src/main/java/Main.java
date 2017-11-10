import model.Class;
import model.Lesson;
import reader.LessonReader;
import reader.TeacherReader;
import state.Classroom;
import state.Hour;
import state.Schedule;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Schedule lastSchedule;

    public static void main(String[] args) {

        Schedule.LESSONS = LessonReader.readLessons(new File("lessons.csv"));
        Schedule.TEACHERS = TeacherReader.readTeachers(new File("teachers.csv"));

        Schedule.CLASSROOMS = new ArrayList<>();
        for (Class clazz : Class.values()) {
            for (int i = 1; i <= 3; i++) {
                Schedule.CLASSROOMS.add(new Classroom(clazz, i));
            }
        }

        Schedule.ALL_HOURS = new ArrayList<>();
        Schedule.HOURS = new ArrayDeque<>();
        for (int day = 1; day <= 5; day++) {
            for (int hour = 1; hour <= 7; hour++) {
                Schedule.ALL_HOURS.add(new Hour(day, hour));
                Schedule.HOURS.add(new Hour(day, hour));
            }
        }

        for (Class clazz : Class.values()) {
            clazz.setHoursNeededPerWeek(Schedule.LESSONS.stream().filter(lesson -> lesson.getaClass() == clazz).map(Lesson::getHoursPerWeek).reduce((a1, a2) -> a1 + a2).orElse(0));
        }

        for (Lesson lesson : Schedule.LESSONS) {
            if (Schedule.TEACHERS.stream().noneMatch(it -> it.getTeachableLessons().contains(lesson.getId()))) {
                System.err.println("Nobody can teach lesson " + lesson.getId() + " " + lesson.getName() + "!");
            }
        }

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.nextLine() != null) {
                System.out.println(lastSchedule.toString());
            }
        }).start();

        System.out.println("Available lessons: " + Schedule.LESSONS.size());
        System.out.println("Available teachers: " + Schedule.TEACHERS.size());

        long timeNow = System.currentTimeMillis();
        System.out.println("Started calculations at: " + timeNow);

        ArrayDeque<Schedule> traverseList = new ArrayDeque<>();
        traverseList.add(new Schedule(0));
        while (!traverseList.isEmpty()) {
            Schedule current = traverseList.poll();
            current.generateChildren();
            if (current.isLeaf()) {
                System.out.println(current.toString());
                continue;
            }
            //System.out.println(current.toString());
            int minCost = current.getChildren().stream().map(Schedule::getCost).min(Integer::compareTo).orElse(0);
            current.getChildren().stream().filter(it -> it.getCost() == minCost).forEach(traverseList::add);
            lastSchedule = current;
        }

        System.out.println("Started ended at: " + System.currentTimeMillis());
        System.out.println("This only took: " + (System.currentTimeMillis() - timeNow) / 1000 + "s");
    }

}
