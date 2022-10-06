import by.AlexAzyavchikov.quizer.*;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        MathTask task = new EquationMathTask(0, MathTask.Operator.MULTIPLICATION, 0);
//        switch (task.validate("")) {
//            case OK -> {
//                System.out.println("OK");
//            }
//            case WRONG -> {
//                System.out.println("WRONG");
//            }
//            case INCORRECT_INPUT -> {
//                System.out.println("INCORRECT_INPUT");
//            }
//        }
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 12; ++i) {
            try {
                EnumSet<MathTask.Operator> operators;
                operators = EnumSet.of(MathTask.Operator.DIVISION);
                EquationMathTaskGenerator generator1 = new EquationMathTaskGenerator(-5, 5, operators, 1);
                Task task = generator1.generate();
                System.out.println("TASK: " + task.getText());
                String answer = scanner.next();
                switch (task.validate(answer)) {
                    case OK -> {
                        System.out.println("OK");
                    }
                    case WRONG -> {
                        System.out.println("WRONG");
                    }
                    case INCORRECT_INPUT -> {
                        System.out.println("INCORRECT_INPUT");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
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