package by.polina_kostyukovich.quizer;

import by.polina_kostyukovich.quizer.tasks.Task;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        Map<String, Quiz> quizMap = Quiz.getQuizMap(args[0]);
        System.out.print("Введите название теста: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String testName = reader.readLine();
        while (testName.equals("") || !quizMap.containsKey(testName)
                || quizMap.get(testName) == null || quizMap.get(testName).generatorThrewException()) {
            if (testName.equals("") || !quizMap.containsKey(testName) || quizMap.get(testName) == null) {
                System.out.print("Теста с таким названием нет(. Повторите попытку: ");
            } else {
                System.out.print("Этот тест некорректен, поэтому не может быть запущен. Выберите другой тест: ");
            }
            testName = reader.readLine();
        }
        Quiz activeQuiz = quizMap.get(testName);

        while (!activeQuiz.isFinished()) {
            Task task = activeQuiz.nextTask();
            System.out.print(task.getText());
            Result result = activeQuiz.provideAnswer(reader.readLine());
            switch (result) {
                case OK -> {
                    System.out.println("Замечательно!");
                }
                case WRONG -> {
                    System.out.println("Неверно! Правильный ответ: " + task.getAnswer());
                }
                case INCORRECT_INPUT -> {
                    System.out.println("Неправильный ввод. Попробуйте еще раз.");
                }
            }
        }
        System.out.println("Отличная работа! Ваша оценка: " + activeQuiz.getMark());
    }
}