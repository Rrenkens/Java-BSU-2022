import by.AlexAzyavchikov.quizer.*;
import by.AlexAzyavchikov.quizer.task_generators.*;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.*;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.*;

import static by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        assert quizMap != null;

        System.out.println("Available quizzes:");
        for (String quizName : quizMap.keySet()) {
            System.out.println("TEST:\t" + quizName);
        }
        System.out.println("");

        Quiz usingQuiz = null;
        System.out.println("Input test name, please:");
        Scanner scanner = new Scanner(System.in);
        String testName = scanner.next();
        while (!quizMap.containsKey(testName)) {
            System.out.println("Input CORRECT test name, please:");
            testName = scanner.next();
        }
        System.out.println("Using test name: \"" + testName + "\"");
        usingQuiz = quizMap.get(testName);

        while (!usingQuiz.isFinished()) {
            Task task = usingQuiz.nextTask();
            System.out.println(task.getText());
            String answer = scanner.next();
            switch (usingQuiz.provideAnswer(answer)) {
                case OK -> {
                    System.out.println("OK");
                }
                case WRONG -> {
                    System.out.println("WRONG");
                }
                case INCORRECT_INPUT -> {
                    System.out.println("INCORRECT_INPUT, try again:");
                }
            }
        }
        System.out.println("Total mark: " + usingQuiz.getMark());
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
//        TODO (AZUAVCHIKOV)
        Map<String, Quiz> quizMap = new HashMap<>();
        {
            //tasks: a+b=?
            List<TaskGenerator> generators = new LinkedList<>();
            generators.add(new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.SUM), 0));

            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(generators);
            quizMap.put("SumExpressionsTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a-b=?
            List<TaskGenerator> generators = new LinkedList<>();
            generators.add(new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIFFERENCE), 0));

            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(generators);
            quizMap.put("DifferenceExpressionsTest", new Quiz(groupGenerator, 10));
        }
        return quizMap;
    }
}