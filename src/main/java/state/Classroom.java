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
