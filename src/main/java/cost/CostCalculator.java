package cost;

import state.Classroom;
import state.Hour;
import state.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static state.Schedule.*;

public class CostCalculator {

    public static final int NO_LESSON_COST = 10;
    public static final int UNBALANCED_COST = 10;
    public static final int UNBALANCED_TEACHER_COST = 10;

    public static int calculateCost(Schedule schedule) {

        final int[] totalCost = {0};

        //Evaluate emtpy hours - evaluate hours per day.
        CLASSROOMS.forEach(classroom -> {
            final List<Integer> hoursPerDay = new ArrayList<>();
            for (int day = 1; day <= 5; day++) {

                //For each day
                int finalDay = day;
                final int[] startLessons = {0};
                final int[] endLessons = {0};
                final List<Integer> lessons = new ArrayList<>();

                ALL_HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (schedule.getScheduleParts().containsKey(hour) && schedule.getScheduleParts().get(hour).isTaught(classroom)) {
                        if (startLessons[0] == 0)
                            startLessons[0] = hour.getHour();
                        endLessons[0] = hour.getHour();
                        lessons.add(hour.getHour());
                    }
                });
                if (startLessons[0] != 0) {
                    for (int i = startLessons[0]; i <= endLessons[0]; i++) {
                        if (!lessons.contains(i))
                            totalCost[0] += NO_LESSON_COST;
                    }
                    hoursPerDay.add(lessons.size());
                }
            }

            //Calculate imbalances in schedule
            totalCost[0] += (hoursPerDay.stream().max(Integer::compareTo).orElse(0) - hoursPerDay.stream().min(Integer::compareTo).orElse(0)) * UNBALANCED_COST;
        });

        //Teacher hour per week balance.
        totalCost[0] += (TEACHERS.stream().map(schedule::totalTaught).max(Integer::compareTo).orElse(0)
                - TEACHERS.stream().map(schedule::totalTaught).min(Integer::compareTo).orElse(0)) * UNBALANCED_TEACHER_COST;

        return totalCost[0];
    }
}
