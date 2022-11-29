package by.Savenok.quizer.task_generators.math_task_generators;

import by.Savenok.quizer.Randomizer;
import by.Savenok.quizer.task_generators.TaskGenerator;
import by.Savenok.quizer.tasks.maths_tasks.ExpressionTask;

public class EquationTaskGenerator implements TaskGenerator {
    public EquationTaskGenerator(
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

    private String ParseTask(int left_side_number,
                             int answer,
                             Randomizer.XPosition position,
                             char operator) {
        String ans = "x";
        if (position == Randomizer.XPosition.FIRST) {
            ans = "x" + operator + Integer.toString(left_side_number);
        } else {
            ans = Integer.toString(left_side_number) + operator + "x";
        }
        ans += "=" + Integer.toString(answer);
        return ans;
    }

    public ExpressionTask generate() {
        int left_side_number = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        int answer = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        Randomizer.XPosition position = Randomizer.GetXPosition();
        char operator = Randomizer.GenerateOperator(generateSum_,
                generateDifference_,
                generateMultiplication_,
                generateDivision_);
        return new ExpressionTask(ParseTask(left_side_number, answer, position, operator),
                CalculateAnswer(left_side_number, answer, position, operator));
    }

    String CalculateAnswer(int left_side_number,
                           int answer,
                           Randomizer.XPosition position,
                           char operator) {
        if (operator == '+') {
            return Integer.toString(answer - left_side_number);
        }
        if (operator == '-') {
            if (position == Randomizer.XPosition.FIRST) {
                return Integer.toString(answer + left_side_number);
            } else {
                return Integer.toString(left_side_number - answer);
            }
        }
        if (operator == '*') {
            if (answer == 0 && left_side_number != 0) {
                return "0";
            } else {
                if (answer == 0) {
                    return "EVERY";
                }
            }
            if (left_side_number == 0) {
                return "NO";
            }
            return Integer.toString(answer / left_side_number);
        }
        if (operator == '/') {
            if (position == Randomizer.XPosition.FIRST) {
                if (left_side_number == 0) {
                    return "NO";
                } else {
                    if (answer == 0) {
                        return "0";
                    } else {
                        return Integer.toString(answer * left_side_number);
                    }
                }
            } else {
                if (left_side_number == 0) {
                    if (answer != 0) {
                        return "NO";
                    } else {
                        return "EVERY";
                    }
                } else {
                    if (answer == 0) {
                        return "NO";
                    } else {
                        return Integer.toString(left_side_number / answer);
                    }
                }
            }
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
