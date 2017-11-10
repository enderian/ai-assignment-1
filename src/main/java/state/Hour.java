package state;

public class Hour {

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
        return day + ":" + hour;
    }
}
