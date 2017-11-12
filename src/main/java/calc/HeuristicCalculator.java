package calc;

import state.Schedule;

import java.util.ArrayList;
import java.util.List;

import static state.Schedule.CLASSROOMS;
import static state.Schedule.HOURS;
import static state.Schedule.TEACHERS;

public class HeuristicCalculator {

    public static final int TEACHER_UNBALANCED_COST = 1000;
    public static final int TEACHER_DAY_OVER_HOURS_COST = 100;
    public static final int TEACHER_OVER_HOURS_COST = 100;
    public static final int TEACHER_NO_BREAK_COST = 100;

    public static final int EMPTY_HOUR_COST = 1000;
    public static final int UNBALANCED_COST = 10;

    public static int calculate(Schedule next) {

        final int[] totalCost = {0};

        ArrayList<Integer> hoursTaught = new ArrayList<>();

        TEACHERS.forEach(teacher -> {

            for (int day = 1; day <= 5; day++) {
                totalCost[0] += Math.max(0, next.totalTaught(teacher, day) - teacher.getMaxHoursPerDay()) * TEACHER_DAY_OVER_HOURS_COST;

                int finalDay = day;
                int[] teachingStreak = {0};
                HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (next.getSchedulePart(hour).isTeaching(teacher)) {
                        teachingStreak[0]++;
                    } else {
                        teachingStreak[0] = 0;
                    }
                    if (teachingStreak[0] >= 3) {
                        totalCost[0] += (teachingStreak[0] - 2) * TEACHER_NO_BREAK_COST;
                    }
                });
            }

            int taught = next.totalTaught(teacher);
            hoursTaught.add(taught);
            totalCost[0] += Math.max(0, taught - teacher.getMaxHoursPerWeek()) * TEACHER_OVER_HOURS_COST;

        });

        CLASSROOMS.forEach(classroom -> {
            for (int day = 1; day <= 5; day++) {
                List<Integer> allHours = new ArrayList<>();
                final int[] startingHour = {0};
                final int[] endingHour = {0};

                int finalDay = day;
                HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (next.getSchedulePart(hour).isTaught(classroom)) {
                        if (startingHour[0] == 0) {
                            startingHour[0] = hour.getHour();
                        }
                        endingHour[0] = hour.getHour();
                        allHours.add(hour.getHour());
                    }
                });

                for (int hour = startingHour[0]; hour <= endingHour[0]; hour++) {
                    if (!allHours.contains(hour)) {
                        totalCost[0] += EMPTY_HOUR_COST;
                    }
                }
            }
        });

        totalCost[0] += Math.max(0, hoursTaught.stream().max(Integer::compareTo).orElse(0) - hoursTaught.stream().min(Integer::compareTo).orElse(0)) * TEACHER_UNBALANCED_COST;
        return totalCost[0];
    }

}
