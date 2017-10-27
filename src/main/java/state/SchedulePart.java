package state;

import model.Lesson;

import java.util.HashMap;
import java.util.Map;

public class SchedulePart {

    private Map<Classroom, Teaching> teachings = new HashMap<>();

    public Map<Classroom, Teaching> getTeachings() {
        return teachings;
    }
}
