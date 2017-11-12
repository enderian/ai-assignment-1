import model.Class;
import model.Lesson;
import model.Teacher;
import org.junit.Assert;
import org.junit.Test;
import state.Hour;
import state.Teaching;

import java.util.Collections;

public class EqualityTests {

    @Test
    public void testTeachings() {

        Teacher teacher = new Teacher(1, "Pagkalos", Collections.singletonList(1), 1, 1);
        Lesson lesson = new Lesson(1, "HelloWorld", Class.A, 1);
        Hour hour = new Hour(1, 2);
        Hour hour2 = new Hour(1, 3);

        Teaching teaching = new Teaching(hour, lesson, teacher);
        Teaching teaching2 = new Teaching(hour2, lesson, teacher);

        Assert.assertEquals(teaching, teaching2);
    }

}
