package state;

import java.util.Comparator;

public class ScheduleComparator implements Comparator<Schedule> {

    @Override
    public int compare(Schedule o1, Schedule o2) {
        return Integer.compare(o1.priority, o2.priority);
    }
}
