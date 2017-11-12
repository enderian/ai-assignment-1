/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
package calc;

import model.Teacher;
import state.Classroom;
import state.Schedule;
import state.Teaching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static state.Schedule.CLASSROOMS;
import static state.Schedule.HOURS;
import static state.Schedule.TEACHERS;

public class HeuristicCalculator {

    public static final int TEACHER_DAY_OVER_HOURS_COST = 300;
    public static final int TEACHER_OVER_HOURS_COST = 300;
    public static final int TEACHER_NO_BREAK_COST = 200;
    public static final int EMPTY_HOUR_COST = 200;

    public static final int TEACHER_UNBALANCED_COST = 2;
    public static final int UNBALANCED_COST = 2;

    public static int MAX_DAY = 5;
    public static boolean DEBUG = false;

    public static int calculate(Schedule next) {

        final int[] totalCost = {0};

        Map<Teacher, Integer> hoursTaught = new HashMap<>();
        TEACHERS.forEach(teacher -> {

            for (int day = 1; day <= MAX_DAY; day++) {
                totalCost[0] += Math.max(0, next.totalTaught(teacher, day) - teacher.getMaxHoursPerDay()) * TEACHER_DAY_OVER_HOURS_COST;

                int finalDay = day;
                int[] teachingStreak = {0};
                HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (CLASSROOMS.stream().anyMatch(classroom -> {
                        Teaching teaching = next.teachings[CLASSROOMS.indexOf(classroom)][HOURS.indexOf(hour)];
                        return teaching != null && teaching.teacher == teacher;
                    })) {
                        teachingStreak[0]++;
                    } else {
                        teachingStreak[0] = 0;
                    }
                    if (teachingStreak[0] >= 3) {
                        if (DEBUG) System.out.println(teacher.getId() + " has taught " + teachingStreak[0] + " hours straight. +" + TEACHER_NO_BREAK_COST);
                        totalCost[0] += TEACHER_NO_BREAK_COST;
                    }
                });
            }

            int taught = next.totalTaught(teacher);
            hoursTaught.put(teacher, taught);
            if (Math.max(0, taught - teacher.getMaxHoursPerWeek()) > 0) {
                if (DEBUG) System.out.println(teacher.getId() + " has taught " + Math.max(0, taught - teacher.getMaxHoursPerWeek()) + " hours total. +" + TEACHER_OVER_HOURS_COST);
                totalCost[0] += Math.max(0, taught - teacher.getMaxHoursPerWeek()) * TEACHER_OVER_HOURS_COST;
            }
        });

        CLASSROOMS.forEach(classroom -> {
            int classroomIndex = CLASSROOMS.indexOf(classroom);
            for (int day = 1; day <= MAX_DAY; day++) {
                List<Integer> allHours = new ArrayList<>();
                final int[] startingHour = {0};
                final int[] endingHour = {0};

                int finalDay = day;
                HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (next.teachings[classroomIndex][HOURS.indexOf(hour)] != null) {
                        if (startingHour[0] == 0) {
                            startingHour[0] = hour.getHour();
                        }
                        endingHour[0] = hour.getHour();
                        allHours.add(hour.getHour());
                    }
                });

                for (int hour = startingHour[0]; hour <= endingHour[0]; hour++) {
                    if (!allHours.contains(hour)) {
                        if (DEBUG) System.out.println(classroom + " has an empty hour at " + day + ":" + hour + " +" + EMPTY_HOUR_COST);
                        totalCost[0] += EMPTY_HOUR_COST;
                    }
                }
            }
        });

        return totalCost[0];
    }

    public static int calculateLight(Schedule next) {

        final int[] totalCost = {0};
        Map<Teacher, Integer> hoursTaught = new HashMap<>();

        TEACHERS.forEach(teacher -> {
            int taught = next.totalTaught(teacher);
            hoursTaught.put(teacher, taught);
        });

        CLASSROOMS.forEach(classroom -> {
            int classroomIndex = CLASSROOMS.indexOf(classroom);
            ArrayList<Integer> hoursPerDay = new ArrayList<>();
            for (int day = 1; day <= MAX_DAY; day++) {
                int finalDay = day;
                final int[] total = {0};
                HOURS.stream().filter(it -> it.getDay() == finalDay).forEach(hour -> {
                    if (next.teachings[classroomIndex][HOURS.indexOf(hour)] != null) {
                        total[0]++;
                    }
                });
                hoursPerDay.add(total[0]);
            }

            int maxDiffHours = hoursPerDay.stream().max(Integer::compareTo).orElse(0) -
                    hoursPerDay.stream().min(Integer::compareTo).orElse(0);
            totalCost[0] += maxDiffHours * UNBALANCED_COST;
        });

        int maxDiff = hoursTaught.values().stream().max(Integer::compareTo).orElse(0) -
                hoursTaught.values().stream().min(Integer::compareTo).orElse(0);
        if (DEBUG) {
            hoursTaught.forEach((key, value) -> System.out.println(key.getName() + " has taught for " + value + " hours."));
            System.out.println("Maximum difference: " + maxDiff + " +" + (maxDiff * UNBALANCED_COST));
        }

        totalCost[0] += maxDiff * TEACHER_UNBALANCED_COST;
        return totalCost[0];
    }



}
