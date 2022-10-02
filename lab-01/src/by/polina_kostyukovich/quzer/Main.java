package by.polina_kostyukovich.quzer;

import by.polina_kostyukovich.quzer.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.print("Введите название теста: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String testName = reader.readLine();
        while (testName.equals("") || !quizMap.containsKey(testName)) {
            System.out.print("Теста с таким названием нет(. Повторите попытку: ");
            testName = reader.readLine();
        }
        Quiz activeQuiz = quizMap.get(testName);

        while (!activeQuiz.isFinished()) {
            Task task = activeQuiz.nextTask();
            System.out.println(task.getText());
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

    static Map<String, Quiz> getQuizMap() {
        return null;
    }
}