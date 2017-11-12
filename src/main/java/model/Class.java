/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
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
