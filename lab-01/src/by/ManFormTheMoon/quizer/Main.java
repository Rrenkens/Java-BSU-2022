package by.ManFormTheMoon.quizer;

import by.ManFormTheMoon.quizer.constants.EquationConstants;
import by.ManFormTheMoon.quizer.constants.ExpressionConstants;
import by.ManFormTheMoon.quizer.constants.GroupConstants;
import by.ManFormTheMoon.quizer.constants.TextConstants;
import by.ManFormTheMoon.quizer.task_generators.GroupTaskGenerator;
import by.ManFormTheMoon.quizer.task_generators.PoolTaskGenerator;
import by.ManFormTheMoon.quizer.tasks.math_task.EquationTask;
import by.ManFormTheMoon.quizer.tasks.math_task.ExpressionTask;
import by.ManFormTheMoon.quizer.tasks.math_task.MathTask;

import java.util.*;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    public static Map<String, Quiz> getQuizMap() {
        ExpressionTask.Generator expressionGenerator =
                new ExpressionTask.Generator(
                        ExpressionConstants.MIN_NUMBER,
                        ExpressionConstants.MAX_NUMBER,
                        EnumSet.allOf(MathTask.Operation.class)
                );
        EquationTask.Generator equationGenerator =
                new EquationTask.Generator(
                        EquationConstants.MIN_NUMBER,
                        EquationConstants.MAX_NUMBER,
                        EnumSet.allOf(MathTask.Operation.class)
                );
        PoolTaskGenerator poolTaskGeneratorWithoutDuplicate = new PoolTaskGenerator(
                false,
                TextConstants.FIRST_TASK,
                TextConstants.SECOND_TASK,
                TextConstants.THIRD_TASK,
                TextConstants.FOURTH_TASK,
                TextConstants.FIFTH_TASK,
                TextConstants.SIXTH_TASK
                );
        PoolTaskGenerator poolTaskGeneratorWithDuplicate = new PoolTaskGenerator(
                true,
                TextConstants.FIRST_TASK,
                TextConstants.SECOND_TASK,
                TextConstants.THIRD_TASK,
                TextConstants.FOURTH_TASK,
                TextConstants.FIFTH_TASK,
                TextConstants.SIXTH_TASK
        );
        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(
                expressionGenerator,
                equationGenerator,
                poolTaskGeneratorWithDuplicate
        );




        Quiz expressionQuiz = new Quiz(expressionGenerator, ExpressionConstants.COUNT_QUESTIONS);
        Quiz equationQuiz = new Quiz(equationGenerator, EquationConstants.COUNT_QUESTIONS);
        Quiz textWithoutDuplicateQuiz = new Quiz(poolTaskGeneratorWithoutDuplicate, TextConstants.COUNT_QUESTIONS);
        Quiz textWithDuplicateQuiz = new Quiz(poolTaskGeneratorWithDuplicate, TextConstants.COUNT_QUESTIONS);
        Quiz groupQuiz = new Quiz(groupTaskGenerator, GroupConstants.COUNT_QUESTIONS);

        Map<String, Quiz> quizMap = new TreeMap<>();
        quizMap.put("expressionQuiz", expressionQuiz);
        quizMap.put("equationQuiz", equationQuiz);
        quizMap.put("textWithoutDuplicateQuiz", textWithoutDuplicateQuiz);
        quizMap.put("textWithDuplicateQuiz", textWithDuplicateQuiz);
        quizMap.put("groupQuiz", groupQuiz);
        return quizMap;
    }

    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Please enter test name");

        Scanner scanner = new Scanner(System.in);

        String quizName;
        while (true) {
            quizName = scanner.nextLine();
            if (!quizMap.containsKey(quizName)) {
                System.out.println("Try again...");
            } else {
                break;
            }
        }

        Quiz quiz = quizMap.get(quizName);
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            String ans = scanner.nextLine();
            Result result = quiz.provideAnswer(ans);
            switch (result) {
                case OK: {
                    System.out.println("OK!");
                    break;
                }
                case WRONG: {
                    System.out.println("Wrong!");
                    break;
                }
                case INCORRECT_INPUT:  {
                    System.out.println("Incorrect. Try again!");
                    break;
                }
            }
        }
        System.out.println("count correct : " + quiz.getCorrectAnswerNumber());
        System.out.println("count wrong  : " + quiz.getWrongAnswerNumber());
        System.out.println("count incorrect input : " +
                quiz.getIncorrectInputNumber());

        Formatter formatter = new Formatter();
        formatter.format("%.1f", quiz.getMark());
        System.out.println("final mark : " + formatter);
    }
}