package state;

import cost.CostCalculator;
import model.Class;
import model.Lesson;
import model.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class Schedule {

    public static List<Hour> ALL_HOURS;
    public static ArrayDeque<Hour> HOURS;
    public static List<Classroom> CLASSROOMS;
    public static List<Lesson> LESSONS;
    public static List<Teacher> TEACHERS;

    private int depth;
    private int cost = 0;
    private boolean leaf;

    private List<Schedule> children = new ArrayList<Schedule>();
    private Map<Hour, HourSchedule> scheduleParts = new HashMap<>();

    public Schedule(int depth) {
        this.depth = depth;
    }

    public List<Schedule> getChildren() {
        return children;
    }

    public void generateChildren() {

        Hour nextHour = HOURS.peek();

        while (true) {
            Hour finalNextHour = nextHour;
            Optional<Classroom> classRoomOptional = CLASSROOMS.stream()
                    .filter(it -> !(scheduleParts.containsKey(finalNextHour) && scheduleParts.get(finalNextHour).getTeachings().containsKey(it)) && totalTaught(it) < it.getaClass().getHoursNeededPerWeek())
                    .findFirst();

            calcBlock:
            {

                if (!classRoomOptional.isPresent()) {
                    //No classroom is left empty at this hour.
                    HOURS.poll();

                    //System.out.println("No more classrooms available.");
                    if (HOURS.isEmpty()) {
                        //All hours are done.
                        leaf = true;
                        return;
                    } else {
                        nextHour = HOURS.peek();
                        //System.out.println("Changing hour: " + nextHour);
                        break calcBlock;
                    }
                }

                //We have a classroom to assign a lesson to.
                Classroom classroom = classRoomOptional.get();

                List<Lesson> lessons = LESSONS.stream()
                        .filter(lesson1 -> lesson1.getaClass() == classroom.getaClass() && hoursTaught(classroom, lesson1) < lesson1.getHoursPerWeek())
                        .collect(Collectors.toList());

                for (Lesson lesson : lessons) {
                    List<Teacher> teachers = TEACHERS.stream()
                            .filter(teacher -> teacher.getTeachableLessons().contains(lesson.getId()) && canTeach(finalNextHour, teacher))
                            .collect(Collectors.toList());

                    for (Teacher teacher : teachers) {
                        Schedule schedule = new Schedule(depth + 1);

                        //System.out.println(teacher.getName() + " can teach " + lesson.getName() + " at " + nextHour + " in " + classroom);
                        schedule.scheduleParts.putAll(scheduleParts);
                        if (!schedule.scheduleParts.containsKey(nextHour)) {
                            schedule.scheduleParts.put(nextHour, new HourSchedule());
                        }
                        HourSchedule hourSchedule = schedule.scheduleParts.get(nextHour);
                        hourSchedule.getTeachings().put(classroom, new Teaching(lesson, teacher));
                        children.add(schedule);
                    }

                    if (teachers.isEmpty()) {
                        //No teachers available.
                        HOURS.poll();

                        //System.out.println("No more teachers available.");
                        if (HOURS.isEmpty()) {
                            //All hours are done.
                            leaf = true;
                            return;
                        } else {
                            nextHour = HOURS.peek();
                            //System.out.println("Changing hour: " + nextHour);
                            break calcBlock;
                        }
                    }
                }
                if (lessons.size() != 0) {
                    Schedule schedule = new Schedule(depth + 1);
                    schedule.scheduleParts.putAll(scheduleParts);
                    if (!schedule.scheduleParts.containsKey(nextHour)) {
                        schedule.scheduleParts.put(nextHour, new HourSchedule());
                    }
                    HourSchedule hourSchedule = schedule.scheduleParts.get(nextHour);
                    hourSchedule.getTeachings().put(classroom, null);

                    schedule.cost = cost + CostCalculator.calculateCost(schedule);

                    children.add(schedule);
                }
                return;
            }
        }
    }

    public boolean canTeach(Hour finalNextHour, Teacher teacher) {
        int todayHours = (int) scheduleParts.entrySet().stream().filter(it -> it.getKey().getDay() == finalNextHour.getDay() && it.getValue().isTeaching(teacher)).count();
        int totalHours = totalTaught(teacher);

        return !(scheduleParts.containsKey(finalNextHour) && scheduleParts.get(finalNextHour).isTeaching(teacher)) && totalHours < teacher.getMaxHoursPerWeek() && todayHours < teacher.getMaxHoursPerDay();
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

    public boolean isLeaf() {
        return leaf;
    }

    @Override
    public String toString() {
        return "Schedule: \n" + scheduleParts.entrySet().stream().map((entry) -> entry.getKey() + ": " + entry.getValue().toString()).reduce((a1, a2) -> a1 + a2).orElse("");
    }

    public int getDepth() {
        return depth;
    }

    public int getCost() {
        return cost;
    }

    public Map<Hour, HourSchedule> getScheduleParts() {
        return scheduleParts;
    }
}
