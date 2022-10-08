import by.AlexAzyavchikov.quizer.*;
import by.AlexAzyavchikov.quizer.task_generators.*;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.*;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.TextTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.EquationMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.ExpressionMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        if (quizMap == null) {
            throw new RuntimeException("quizMap == null");
        }

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
        System.out.println("Using test: \"" + testName + "\"");
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
        Map<String, Quiz> quizMap = new LinkedHashMap<>();
        //ExpressionTests
        {
            //tasks: a+b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.SUM), 0));
            quizMap.put("ExpressionSumTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a-b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIFFERENCE), 0));
            quizMap.put("ExpressionDifferenceTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a*b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.MULTIPLICATION), 0));
            quizMap.put("ExpressionMultiplicationTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a/b=?
            /*
            Note that:
            x/0 = Infinity
            -x/0 = Infinity
            0/0 = NaN
             */
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIVISION), 0));
            quizMap.put("ExpressionDivisionTest", new Quiz(groupGenerator, 10));
        }
        //EquationTests
        {
            //tasks: a+x=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.SUM), 0));
            quizMap.put("EquationSumTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a-x=b or x-a=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIFFERENCE), 0));
            quizMap.put("EquationDifferenceTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a*x=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.MULTIPLICATION), 0));
            quizMap.put("EquationMultiplicationTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a/x=b or x/a=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIVISION), 0));
            quizMap.put("EquationDivisionTest", new Quiz(groupGenerator, 10));
        }
        //TextTests
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(false,
                            new TextTask("print lol", "lol"),
                            new TextTask("Print lol", "lol"),
                            new TextTask("print lol", "lol"),
                            new TextTask("print lol", "lol"),
                            new TextTask("print kek", "kek")));
            quizMap.put("TextTest", new Quiz(groupGenerator, 3));
        }
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(true,
                            new TextTask("print lol", "lol"),
                            new TextTask("print lol", "kek")));
            quizMap.put("TrickyTextTest", new Quiz(groupGenerator, 5));
        }
        //MyTextTest
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new MyTaskGenerator(10));
            quizMap.put("CringeTextTest", new Quiz(groupGenerator, 5));
        }
        return quizMap;
    }
}