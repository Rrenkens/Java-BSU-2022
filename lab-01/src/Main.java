import by.ZaharKalosha.quizzer.Quiz;
import by.ZaharKalosha.quizzer.Result;
import by.ZaharKalosha.quizzer.task_generators.GroupTaskGenerator;
import by.ZaharKalosha.quizzer.task_generators.PoolTaskGenerator;
import by.ZaharKalosha.quizzer.tasks.TextTask;
import by.ZaharKalosha.quizzer.tasks.math_tasks.EquationTask;
import by.ZaharKalosha.quizzer.tasks.math_tasks.ExpressionTask;
import by.ZaharKalosha.quizzer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> listOfQuizzes = getQuizMap();
        printListOfQuizzes(listOfQuizzes);
        System.out.println("Введите название квиза.");
        Scanner scanner = new Scanner(System.in);
        String quizNameFromCLI = scanner.nextLine();
        Quiz currentQuiz = null;

        boolean correctnessCheck = false;
        while (!correctnessCheck) {
            if (!listOfQuizzes.containsKey(quizNameFromCLI)) {
                System.out.println("Тест не найден. Пожалуйста проверьте введенное название и повторите еще раз...");
                quizNameFromCLI = scanner.nextLine();
            } else {
                currentQuiz = listOfQuizzes.get(quizNameFromCLI);
                correctnessCheck = true;
            }
        }

        checkingTaskForCompletion(currentQuiz);

        getQuizStatistics(currentQuiz);
    }

    static void printListOfQuizzes(Map<String, Quiz> listOfQuizzes) {
        System.out.println("Список доступных квизов:");
        listOfQuizzes.forEach((name, quiz) -> System.out.println(name));
        System.out.println();
    }

    static void checkingTaskForCompletion(Quiz currentQuiz) {
        while (!currentQuiz.isFinished()) {
            System.out.println(currentQuiz.nextTask().getText());
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            Result result = currentQuiz.provideAnswer(answer);
            switch (result) {
                case OK -> System.out.println("Ответ верен");
                case WRONG -> System.out.println("Ответ неверен");
                case INCORRECT_INPUT -> System.out.println("Некорректный формат ответа, попробуйте еще раз");
            }
        }
    }

    static void getQuizStatistics(Quiz currentQuiz) {
        System.out.println("Количество правильных ответов: " + currentQuiz.getCorrectAnswerNumber());
        System.out.println("Количество неправильных ответов: " + currentQuiz.getWrongAnswerNumber());
        System.out.println("Количество некорректных вводов: " + currentQuiz.getIncorrectInputNumber());
        String formattedResult = String.format("%.2f", currentQuiz.getMark());
        System.out.println("Финальная оценка: " + formattedResult);
    }

    static boolean setStrictness() {
        System.out.println("Следует ли проверять регистр текста?(y/n)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean strictCase = false;
        boolean correctnessCheck = false;
        while (!correctnessCheck) {
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("н")) {
                strictCase = true;
                correctnessCheck = true;
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("т")) {
                strictCase = false;
                correctnessCheck = true;
            } else {
                System.out.println("Некорректный ввод, повторите еще раз.");
                input = scanner.nextLine();
            }
        }
        return strictCase;
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        boolean strictCase = setStrictness();
        Map<String, Quiz> result = new HashMap<>();

        ExpressionTask.Generator taskGenerator1 = new ExpressionTask.Generator
                (1, 100, 2, EnumSet.allOf(MathTask.Operation.class));
        Quiz expressionsQuiz = new Quiz(taskGenerator1, 10);
        result.put("Expressions", expressionsQuiz);

        EquationTask.Generator taskGenerator2 = new EquationTask.Generator
                (1, 100, 3, EnumSet.allOf(MathTask.Operation.class), new int[]{0, 1});
        Quiz equationsQuiz = new Quiz(taskGenerator2, 10);
        result.put("Equations", equationsQuiz);

        PoolTaskGenerator taskGenerator3 = new PoolTaskGenerator(false,
                new TextTask("Answer 1 - Qwerty.\"\n", "Qwerty", false),
                new TextTask("Answer 2 - Qwer1y.\"\n", "Qwer1y", false),
                new TextTask("Answer 3 (strict) - -3.15\"\n", "Qwerty", true),
                new TextTask("Answer 4 (strict) - {^?*}.\"\n", "Qwerty", true));
        Quiz textQuiz = new Quiz(taskGenerator3, 4);
        result.put("Text", textQuiz);

        PoolTaskGenerator taskGenerator4 = new PoolTaskGenerator(true,
                new TextTask("Answer 1 (strict, width duplication) - Qwerty.\"\n", "Qwerty", true),
                new TextTask("Answer 2 (strict, width duplication width duplication) - Qwer1y.\"\n", "Qwer1y", true),
                new TextTask("Answer 3 (width duplication) - -3.15\"\n", "Qwerty", false),
                new TextTask("Answer 4 (width duplication) - {^?*}.\"\n", "Qwerty", false));
        Quiz textWithDuplicationsQuiz = new Quiz(taskGenerator4, 8);
        result.put("Text With Duplications", textWithDuplicationsQuiz);

        Quiz MixedQuiz = new Quiz(new GroupTaskGenerator(taskGenerator1, taskGenerator2, taskGenerator4.clone()), 10);
        result.put("Mixed", MixedQuiz);

        return result;
    }
}