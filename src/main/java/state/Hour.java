/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
package state;

public class Hour implements Cloneable {

    private int day;
    private int hour;

    public Hour(int day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return day + "-" + hour;
    }
}
