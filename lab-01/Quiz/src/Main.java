import by.AlexAzyavchikov.quizer.*;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.ExpressionMathTaskGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
//            ExpressionMathTaskGenerator generator = new ExpressionMathTaskGenerator(6, 0, true, true, true, true);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
//    public static void main(String[] args) {
//
//        Map<String, Quiz> quizMap = getQuizMap();
//        assert quizMap != null;
//        Quiz usingQuiz = null;
//
//        System.out.println("Input test name, please:");
//        Scanner scanner = new Scanner(System.in);
//        String testName = scanner.next();
//        while (!quizMap.containsKey(testName)) {
//            System.out.println("Input CORRECT test name, please:");
//            testName = scanner.next();
//        }
//        System.out.println("Using test name: \"" + testName + "\"");
//        usingQuiz = quizMap.get(testName);
//
//        while (!usingQuiz.isFinished()) {
//            Task task = usingQuiz.nextTask();
//            System.out.println(task.getText());
//            String answer = scanner.next();
//            usingQuiz.provideAnswer(answer);
//        }
//        System.out.println("Total mark: " + usingQuiz.getMark());
//    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
//        TODO (AZUAVCHIKOV)
        Map<String, Quiz> quizMap = new HashMap<String, Quiz>();
        quizMap.put("SimpleTest", new Quiz(null, 0));
        return quizMap;
    }
}