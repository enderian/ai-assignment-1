import org.junit.Assert;
import org.junit.Test;
import state.Schedule;
import state.ScheduleComparator;

import java.util.PriorityQueue;

public class ComparatorTest {

    @Test
    public void testPriority() {

        Schedule schedule1 = new Schedule(0);
        schedule1.priority = 12;

        Schedule schedule2 = new Schedule(1);
        schedule2.priority = 5;

        PriorityQueue<Schedule> queue = new PriorityQueue<>(new ScheduleComparator());
        queue.offer(schedule1);
        queue.offer(schedule2);

        Assert.assertEquals(queue.poll().getDepth(), 1);
    }

}
