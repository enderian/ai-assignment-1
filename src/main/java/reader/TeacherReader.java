package reader;

import model.Class;
import model.Lesson;
import model.Teacher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherReader {

    public static List<Teacher> readLessons(File file) {

        List<Teacher> teachers = new ArrayList<>();

        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //We are ignoring the first line.
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                List<Integer> teachableLessons = new ArrayList<>();

                for (int i = 4; i < split.length; i++) {
                    teachableLessons.add(Integer.parseInt(split[i].trim()));
                }
                teachers.add(new Teacher(
                        Integer.parseInt(split[0].trim()),
                        split[1].trim(),
                        teachableLessons,
                        Integer.parseInt(split[2].trim()),
                        Integer.parseInt(split[3].trim())
                ));
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return teachers;
    }

}
