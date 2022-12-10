package by.toharrius.quizer;

import by.toharrius.quizer.task_generators.GroupTaskGenerator;
import by.toharrius.quizer.task_generators.PoolTaskGenerator;
import by.toharrius.quizer.tasks.imminence_tasks.GenerousTask;
import by.toharrius.quizer.tasks.TextTask;
import by.toharrius.quizer.tasks.imminence_tasks.TrickyTask;
import by.toharrius.quizer.tasks.math_tasks.EquationTask;
import by.toharrius.quizer.tasks.math_tasks.ExpressionTask;
import by.toharrius.quizer.tasks.math_tasks.MathOperation;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String helpMessage = "list : view available quizes\n" +
            "<test name> : run particular quiz\n" +
            "help : view this message\n" +
            "exit | quit | stop : terminate session";
    private static Map<String, Quiz> quizes;
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link by.toharrius.quizer.Quiz}
     */
    private static @NotNull Map<String, Quiz> getQuizMap() {
        var quizes = new HashMap<String, Quiz>();
        {
            var gen = new PoolTaskGenerator(false,
                    new TextTask("Какая (по слухам) подработка у С*****ча?", "сборщик мусора"),
                    new TextTask("Как называется человек, убегающий от каннибала?", "фастфуд"),
                    new TextTask("Какой вид порно не могут снять бомжи?", "домашнее"),
                    new TextTask("Как называется оглушающий удар татара?", "татарстан"));
            quizes.put("stupid-questions", new Quiz(gen, 4));
        }
        {
            var gen = new PoolTaskGenerator(true,
                    new GenerousTask("Каков правильный ответ?"),
                    new TrickyTask("Это утверждение ложно."));
            quizes.put("know-your-luck", new Quiz(gen, 7));
        }
        {
            var gen_eq_add = new EquationTask.Generator(2, 55,
                    EnumSet.of(MathOperation.ADD));
            var gen_eq_42_all = new EquationTask.Generator(42, 42,
                    EnumSet.allOf(MathOperation.class));
            var gen_ex_div = new ExpressionTask.Generator(17, 42,
                    EnumSet.of(MathOperation.DIVIDE));
            var gen_ex_all = new ExpressionTask.Generator(41, 42,
                    EnumSet.allOf(MathOperation.class));
            var gen_eq = new GroupTaskGenerator(gen_eq_42_all, gen_eq_add);
            var gen_ex = new GroupTaskGenerator(new ExpressionTask.Generator[]{gen_ex_div, gen_ex_all});
            var gen_group = new GroupTaskGenerator(gen_ex, gen_eq,
                    new EquationTask.Generator(gen_eq_42_all, CopyParameter.FLAG));
            quizes.put("tricky-math", new Quiz(gen_group, 5));
        }
        return quizes;
    }

    private static String inputLineNormalized() throws IOException {
        return consoleReader.readLine().toLowerCase().trim();
    }

    private static void tryLaunchQuiz(String query) throws IOException {
            if (quizes.containsKey(query)) {
                System.out.println("Starting quiz \"" + query + "\"!");
                Quiz clone;
                try {
                    clone = new Quiz(quizes.get(query));
                } catch (Exception e) {
                    throw new RuntimeException("Unable to clone", e);
                }
                runProblemSet(clone);
            } else {
                System.out.println("Sorry, not found. Type \"list\" to see available");
            }
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
        System.out.println(helpMessage);
        quizes = getQuizMap();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        boolean interactionFinished = false;
        while (!interactionFinished) {
            var query = inputLineNormalized();
            switch (query) {
                case "list" -> {
                    if (quizes.isEmpty()) {
                        System.out.println("There are no quizes now, coming soon ):");
                    } else {
                        System.out.println("The total of " + quizes.size() + " quizes are present");
                        quizes.forEach((name, q) -> System.out.println(name));
                    }
                }
                case "help" -> {
                    System.out.println(helpMessage);
                }
                case "exit", "quit", "stop", ":q" -> {
                    interactionFinished = true;
                }
                default -> tryLaunchQuiz(query);
            }
        }
    }
    private static BufferedReader consoleReader;
}
