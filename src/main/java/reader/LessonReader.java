package reader;

import model.Class;
import model.Lesson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessonReader {

    public static List<Lesson> readLessons(File file) {

        List<Lesson> lessons = new ArrayList<>();

        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //We are ignoring the first line.
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = trimStringByString(line, ",").split(",");
                if (split.length < 4) continue;

                lessons.add(new Lesson(
                        Integer.parseInt(split[0].trim()),
                        split[1].trim(),
                        Class.valueOf(split[2].trim()),
                        Integer.parseInt(split[3].trim())
                ));
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return lessons;
    }

    public static String trimStringByString(String text, String trimBy) {
        int beginIndex = 0;
        int endIndex = text.length();

        while (text.substring(beginIndex, endIndex).startsWith(trimBy)) {
            beginIndex += trimBy.length();
        }

        while (text.substring(beginIndex, endIndex).endsWith(trimBy)) {
            endIndex -= trimBy.length();
        }

        return text.substring(beginIndex, endIndex);
    }

}
