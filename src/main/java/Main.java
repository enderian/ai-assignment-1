import calc.CostCalculator;
import calc.HeuristicCalculator;
import model.Class;
import model.Lesson;
import reader.LessonReader;
import reader.TeacherReader;
import state.Classroom;
import state.Hour;
import state.Schedule;
import state.ScheduleComparator;

import java.io.File;
import java.util.*;

public class Main {

    private static Schedule bestSchedule;
    private static int lastDepth = 0;

    public static void main(String[] args) {

        Schedule.LESSONS = LessonReader.readLessons(new File("lessons.csv"));
        Schedule.TEACHERS = TeacherReader.readTeachers(new File("teachers.csv"));
        Schedule.CLASSROOMS = new ArrayList<>();

        for (Class clazz : Class.values()) {
            for (int i = 1; i <= 3; i++) {
                Schedule.CLASSROOMS.add(new Classroom(clazz, i));
            }
        }
        Schedule.HOURS = new ArrayList<>();
        for (int day = 1; day <= 5; day++) {
            for (int hour = 1; hour <= 7; hour++) {
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

        System.out.println("Available lessons: " + Schedule.LESSONS.size());
        System.out.println("Available teachers: " + Schedule.TEACHERS.size());

        long timeNow = System.currentTimeMillis();
        System.out.println("Maximum depth will be: " + Schedule.MAX_DEPTH);
        System.out.println("Started calculations at: " + timeNow);

        Schedule schedule = new Schedule(0, 0, 0);
        schedule.generateRandom();
        schedule.setPriority(1);

        Map<Integer, Integer> costSoFar = new HashMap<>();
        costSoFar.put(0, 1);
        Map<Schedule, Schedule> cameFrom = new HashMap<>();
        cameFrom.put(schedule, null);

        Queue<Schedule> traverse = new PriorityQueue<>(new ScheduleComparator());
        traverse.add(schedule);

        System.out.println("Initial state:");
        System.out.println(schedule.toString());

        int iterations = 0;
        while (!traverse.isEmpty()) {
            Schedule current = traverse.poll();
            int lastCost = costSoFar.get(current.getDepth());

            if (HeuristicCalculator.calculate(current) == 0) {
                System.out.println(current.toString());
                System.out.println("Final state.");
                return;
            }

            current.generateChildren();
            current.getChildren().forEach(next -> {
                int newCost = lastCost + CostCalculator.calculate(next);

                if (!costSoFar.containsKey(next.getDepth()) || newCost < costSoFar.get(next.getDepth())) {
                    costSoFar.put(next.getDepth(), newCost);
                    next.setPriority(newCost + HeuristicCalculator.calculate(next));
                    cameFrom.put(next, current);
                    traverse.offer(next);
                }
            });
            current.getChildren().clear();

            System.out.println(iterations++ + " (" + traverse.size() + ") (" + (current.getDepth()) + ")");
            System.out.println(HeuristicCalculator.calculate(current));
        }
        System.out.println(traverse.isEmpty());

        System.out.println("Started ended at: " + System.currentTimeMillis());
        System.out.println("This only took: " + (System.currentTimeMillis() - timeNow) / 1000 + "s");
    }

}
