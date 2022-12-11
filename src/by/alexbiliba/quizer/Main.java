package by.alexbiliba.quizer;

import by.alexbiliba.quizer.tasks.TextTask;
import by.alexbiliba.quizer.task_generators.GroupTaskGenerator;
import by.alexbiliba.quizer.task_generators.PoolTaskGenerator;
import by.alexbiliba.quizer.tasks.math_tasks.EquationTask;
import by.alexbiliba.quizer.tasks.math_tasks.ExpressionTask;
import by.alexbiliba.quizer.tasks.math_tasks.MathTask;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizzes = new HashMap<String, Quiz>();

        EnumSet<MathTask.Operation> enumSet1;
        enumSet1 = EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFFERENCE,
                MathTask.Operation.MULTIPLICATION,
                MathTask.Operation.DIVISION);
        quizzes.put("Only expressions", new Quiz(new ExpressionTask.Generator(0, 1000, 3, enumSet1), 10));

        EnumSet<MathTask.Operation> enumSet2;
        enumSet2 = EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.MULTIPLICATION,
                MathTask.Operation.DIVISION);
        quizzes.put("Only equations", new Quiz(new EquationTask.Generator(0, 2000, 2, enumSet2), 10));

        EnumSet<MathTask.Operation> enumSet3;
        enumSet3 = EnumSet.of(
                MathTask.Operation.MULTIPLICATION,
                MathTask.Operation.DIVISION);
        quizzes.put("Mixed math tasks", new Quiz(new GroupTaskGenerator(new ExpressionTask.Generator(0, 1000, 5, enumSet3),
                new EquationTask.Generator(0, 1000, 6, enumSet3)), 10));

        // Заготовка текстовых заданий
        TextTask task1 = new TextTask("Do you love Java?", "Yes");
        TextTask task2 = new TextTask("Do you love C++?", "Yes");
        TextTask task3 = new TextTask("Do you love Assembly?", "No.....");
        TextTask task4 = new TextTask("Do you love Python?", "Maybe");
        TextTask task5 = new TextTask("Do you love WinAPI?", "No");
        PoolTaskGenerator textTaskGen = new PoolTaskGenerator(false, task1, task2, task3, task4, task5);

        EnumSet<MathTask.Operation> enumSet4;
        enumSet4 = EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFFERENCE);
        quizzes.put("Mixed math tasks", new Quiz(new GroupTaskGenerator(textTaskGen,
                new ExpressionTask.Generator(0, 1000, 10, enumSet4),
                new EquationTask.Generator(0, 1000, 11, enumSet4)), 10));

        quizzes.put("Only text", new Quiz(new PoolTaskGenerator(true, task1, task2, task3, task4, task5), 10));

        return quizzes;
    }
    public static void main(String[] args) {
        Map<String, Quiz> quizzes = getQuizMap();
        Scanner input = new Scanner(System.in);
        String quizName = null;
        while (true) {
            System.out.print("Enter the quiz name: ");
            quizName = input.nextLine();
            if (quizzes.containsKey(quizName)) {
                break;
            }
            System.out.println("There is no quiz with this name!");
        }
        System.out.flush();

        Quiz mainQuiz = quizzes.get(quizName);
        System.out.println("Quiz started!");
        int taskIndex = 1;
        while (!mainQuiz.isFinished()) {
            System.out.print(String.valueOf(taskIndex) + ") ");
            Task currentTask = mainQuiz.nextTask();
            System.out.println(currentTask.getText());
            String userAnswer = input.nextLine();
            mainQuiz.provideAnswer(userAnswer);
            taskIndex++;
        }
        System.out.println("Total:");
        System.out.println("Correct answers - " + String.valueOf(mainQuiz.AC_number));
        System.out.println("Wrong answers - " + String.valueOf(mainQuiz.WA_number));
        System.out.println("Incorrect input - " + String.valueOf(mainQuiz.II_number));
        System.out.println("Your score: " + mainQuiz.getMark() * 100. + "%");
        input.close();
    }
}
