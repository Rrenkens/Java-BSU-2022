package by.Savenok.quizer.task_generators;

import by.Savenok.quizer.tasks.TextTask;

import java.io.*;
import java.util.Scanner;

public class TextTaskGenerator implements TaskGenerator {
    public TextTaskGenerator(DifficultyLevel level) throws FileNotFoundException {

        difficulty_level_ = level;
        if (difficulty_level_ == DifficultyLevel.HARD) {
//            File file = new File("TextTaskStatementsHard.txt");
            File file = new File("D:\\IntelliJ Projects\\Java-BSU-2022\\lab-01\\src\\by\\Savenok\\quizer\\task_generators\\TextTaskStatementsHard.txt");
            scanner_ = new Scanner(file);
        }
        if (difficulty_level_ == DifficultyLevel.NIGHTMARE) {
            File file = new File("D:\\IntelliJ Projects\\Java-BSU-2022\\lab-01\\src\\by\\Savenok\\quizer\\task_generators\\TextTaskStatementsNightmare.txt");
            scanner_ = new Scanner(file);
        }

    }

    public TextTask generate() {
        return new TextTask(scanner_.nextLine(), scanner_.nextLine());
    }

    public enum DifficultyLevel {
        HARD,
        NIGHTMARE
    }

    DifficultyLevel difficulty_level_;
    Scanner scanner_;
}
