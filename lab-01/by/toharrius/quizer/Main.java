package by.toharrius.quizer;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link by.toharrius.quizer.Quiz}
     */
    private static @NotNull Map<String, Quiz> getQuizMap() {
        var map = new HashMap<String, Quiz>();
        return map;
    }

    private static String inputLineNormalized() throws IOException {
        return consoleReader.readLine().toLowerCase().trim();
    }

    private static void runProblemSet(Quiz quiz) throws IOException {
        while (!quiz.isFinished()) {
            var task = quiz.nextTask();
            System.out.println("Task " + (quiz.getCurrentTaskIndex() + 1)
                                + " out of " + quiz.getTaskCount()
                                + ": " + task.getText());
            var answer = inputLineNormalized();
            System.out.println(switch (quiz.provideAnswer(answer)) {
                case OK -> "Nice!";
                case WRONG -> "Nope.";
                case INCORRECT_INPUT -> "Bad input format. Please, try again.";
            });
        }
        System.out.println("Quiz finished! Your statistics:");
        System.out.println("Correct answers: " + quiz.getCorrectAnswerNumber());
        System.out.println("Wrong answers: " + quiz.getWrongAnswerNumber());
        System.out.println("Total tries: " + quiz.getTotalAnswerNumber());
        System.out.println("Final mark: " + quiz.getMark());
    }

    public static void main(String[] args) throws IOException {
        var map = getQuizMap();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        boolean interactionFinished = false;
        while (!interactionFinished) {
            var query = inputLineNormalized();
            switch (query) {
                case "list" -> {
                    if (map.isEmpty()) {
                        System.out.println("There are no quizes now, coming soon ):");
                    } else {
                        System.out.println("The total of " + map.size() + " quizes are present");
                        map.forEach((name, q) -> System.out.println(name));
                    }
                    continue;
                }
                case "help" -> {
                    System.out.println("list : view available quizes\n" +
                            "<test name> : run particular quiz\n" +
                            "help : view this message\n" +
                            "exit | quit | stop : terminate session");
                    continue;
                }
                case "exit", "quit", "stop", ":q" -> {
                    interactionFinished = true;
                    continue;
                }
            }
            if (map.containsKey(query)) {
                System.out.println("Starting quiz \"" + query + "\"!");
                runProblemSet(map.get(query));
            } else {
                System.out.println("Sorry, not found. Type \"list\" to see available");
            }
        }
    }
    private static BufferedReader consoleReader;
}
