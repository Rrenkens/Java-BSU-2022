package by.Savenok.quizer.task_generators.math_task_generators;

import by.Savenok.quizer.Randomizer;
import by.Savenok.quizer.task_generators.TaskGenerator;
import by.Savenok.quizer.tasks.maths_tasks.ExpressionTask;

public class ExpressionTaskGenerator implements TaskGenerator {
    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        minNumber_ = minNumber;
        maxNumber_ = maxNumber;
        generateSum_ = generateSum;
        generateDifference_ = generateDifference;
        generateMultiplication_ = generateMultiplication;
        generateDivision_ = generateDivision;
    }

    private String ParseTask(int first, int second, char operator) {
        String ans = Integer.toString(first);
        ans += operator;
        ans += Integer.toString(second);
        return ans;
    }

    public ExpressionTask generate() {
        int first = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        int second = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        char operator = Randomizer.GenerateOperator(generateSum_,
                generateDifference_,
                generateMultiplication_,
                generateDivision_);
        return new ExpressionTask(ParseTask(first, second, operator),
                CalculateAnswer(first, second, operator));
    }

    String CalculateAnswer(int first, int second, char operator) {
        if (operator == '+') {
            return Integer.toString(first + second);
        }
        if (operator == '-') {
            return Integer.toString(first - second);
        }
        if (operator == '*') {
            return Integer.toString(first * second);
        }
        if (operator == '/') {
            if (second == 0) {
                second = 1;
            }
            return Integer.toString(first / second);
        }
        return "bad input"; // bad
    }

    int minNumber_;
    int maxNumber_;
    boolean generateSum_ = false;
    boolean generateDifference_ = false;
    boolean generateMultiplication_ = false;
    boolean generateDivision_ = false;
}
