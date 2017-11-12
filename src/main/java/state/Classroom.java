/*
    Artificial Intelligence Assignment 1 - Ion Androutsopoulos
    Authored by:
        p3150007 Vasileiou Ismini
        p3150133 Pagkalos Spyridon
 */
package state;

import model.Class;

public class Classroom {

    private Class aClass;
    private int number;

    public Classroom(Class aClass, int number) {
        this.aClass = aClass;
        this.number = number;
    }

    public Class getaClass() {
        return aClass;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return aClass.name() + number;
    }
}
