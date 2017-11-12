/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
package state;

import java.util.Comparator;

public class ScheduleComparator implements Comparator<Schedule> {

    @Override
    public int compare(Schedule o1, Schedule o2) {
        return Integer.compare(o1.priority, o2.priority);
    }
}
