import calc.HeuristicCalculator;
import model.Class;
import model.Lesson;
import model.Teacher;
import org.junit.Test;
import state.Classroom;
import state.Hour;
import state.Schedule;

import java.util.ArrayList;
import java.util.Collections;

public class HeuristicTest {

    @Test
    public void testHeuristic() {

        Schedule.CLASSROOMS = new ArrayList<>();
        Schedule.CLASSROOMS.add(new Classroom(Class.A, 1));
        Schedule.CLASSROOMS.add(new Classroom(Class.A, 2));
        Schedule.CLASSROOMS.add(new Classroom(Class.A, 3));

        Schedule.HOURS = new ArrayList<>();
        for (int day = 1; day <= 2; day++) {
            for (int hour = 1; hour <= 5; hour++) {
                Schedule.HOURS.add(new Hour(day, hour));
            }
        }

        Schedule.LESSONS = new ArrayList<>();
        Schedule.LESSONS.add(new Lesson(1, "Ancient Greek", Class.A, 2));
        Schedule.LESSONS.add(new Lesson(2, "Greek", Class.A, 3));
        Schedule.LESSONS.add(new Lesson(3, "Math", Class.A, 4));
        Schedule.LESSONS.add(new Lesson(4, "Physics", Class.A, 2));
        Schedule.LESSONS.add(new Lesson(5, "Religion", Class.A, 2));
        Schedule.LESSONS.add(new Lesson(6, "Chemistry", Class.A, 2));

        Schedule.TEACHERS = new ArrayList<>();
        Schedule.TEACHERS.add(new Teacher(1, "Pagkalos1", Collections.singletonList(1), 5, 20));
        Schedule.TEACHERS.add(new Teacher(2, "Pagkalos2", Collections.singletonList(2), 5, 20));
        Schedule.TEACHERS.add(new Teacher(3, "Pagkalos3", Collections.singletonList(3), 5, 20));
        Schedule.TEACHERS.add(new Teacher(4, "Pagkalos4", Collections.singletonList(4), 5, 20));
        Schedule.TEACHERS.add(new Teacher(5, "Pagkalos5", Collections.singletonList(5), 5, 20));
        Schedule.TEACHERS.add(new Teacher(6, "Pagkalos6", Collections.singletonList(6), 5, 20));

        Schedule schedule = new Schedule(0);
        schedule.generateRandom();

        HeuristicCalculator.DEBUG = true;
        HeuristicCalculator.MAX_DAY = 2;
        System.out.println(schedule);
        System.out.println(HeuristicCalculator.calculate(schedule));




    }

}
