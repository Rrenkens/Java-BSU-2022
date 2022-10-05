package by.Adamenko.quizer;

import by.Adamenko.quizer.Quiz;
import by.Adamenko.quizer.task_generators.TaskGenerator;
import by.Adamenko.quizer.task_generators.EquationTaskGenerator;
import by.Adamenko.quizer.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Quiz> allTests = Quiz.getQuizMap();
        System.out.println("Введите название теста...");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String in = reader.readLine();
        while (!allTests.containsKey(in)) {
            System.out.println("Тест не найден. Повторите попытку!");
            in = reader.readLine();
        }
        Quiz quiz = allTests.get(in);
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());
            String answer = reader.readLine();
            quiz.provideAnswer(answer);
        }
        System.out.println(quiz.getMark());
    }
}