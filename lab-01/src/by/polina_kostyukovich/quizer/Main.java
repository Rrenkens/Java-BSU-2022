package by.polina_kostyukovich.quizer;

import by.polina_kostyukovich.quizer.tasks.Task;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Map<String, Quiz> quizMap = Quiz.getQuizMap();
        System.out.print("Введите название теста: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String testName = reader.readLine();
        while (testName.equals("") || !quizMap.containsKey(testName) || quizMap.get(testName).generatorThrewException()) {
            if (quizMap.get(testName).generatorThrewException()) {
                System.out.print("Этот тест некорректен, поэтому не может быть запущен. Выберите другой тест: ");
            } else {
                System.out.print("Теста с таким названием нет(. Повторите попытку: ");
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