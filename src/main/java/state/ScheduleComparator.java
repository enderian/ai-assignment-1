package state;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ScheduleComparator implements Comparator<Schedule> {

    @Override
    public int compare(Schedule o1, Schedule o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }
}
