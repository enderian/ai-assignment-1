package model;

public enum Class {
    A, B, C;

    Class() {

    }

    private int hoursNeededPerWeek;

    public int getHoursNeededPerWeek() {
        return hoursNeededPerWeek;
    }

    public void setHoursNeededPerWeek(int hoursNeededPerWeek) {
        this.hoursNeededPerWeek = hoursNeededPerWeek;
    }
}
