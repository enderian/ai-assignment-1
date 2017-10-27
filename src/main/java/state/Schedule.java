package state;

import model.Lesson;
import model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    public static List<Lesson> LESSONS;
    public static List<Teacher> TEACHERS;

    private int depth;
    private List<Schedule> children = new ArrayList<Schedule>();
    private Map<Integer, SchedulePart> scheduleParts = new HashMap<>();
    //For Hour ID=X: Day = X/10, HourOfDay = X%10

    public Schedule(int depth) {
        this.depth = depth;
    }

    public List<Schedule> getChildren() {
        return children;
    }

    public void generateChildren() {
        int nextDay = (depth / 7) + 1;
        int nextHour = (depth % 7) + 1;

        
    }

    public boolean isComplete() {
        Map<Classroom, Integer> hoursTaught = new HashMap<>();
        for (SchedulePart schedulePart: scheduleParts.values()) {
            for (Classroom classroom: schedulePart.getTeachings().keySet()) {
                if (hoursTaught.containsKey(classroom)) {
                    hoursTaught.put(classroom, hoursTaught.get(classroom) + 1);
                } else {
                    hoursTaught.put(classroom, 1);
                }
            }
        }
        return hoursTaught.entrySet().stream().allMatch(classroomIntegerEntry ->
            classroomIntegerEntry.getKey().getaClass().getHoursNeededPerWeek() == classroomIntegerEntry.getValue()
        );
    }

    private int getHourIdForDepth() {
        //Max hours per day = 7
        if (depth == 0) return 0;
        return (((depth - 1) / 7) + 1) * 10 + ((depth - 1) % 7 + 1);
    }
}
