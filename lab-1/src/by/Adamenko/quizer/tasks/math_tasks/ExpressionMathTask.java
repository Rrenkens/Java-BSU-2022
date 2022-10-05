package by.Adamenko.quizer.tasks.math_tasks;

import by.Adamenko.quizer.Operator;
import by.Adamenko.quizer.Result;
import by.Adamenko.quizer.tasks.Task;

public class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(int value_1,
                              int value_2,
                              Operator operator) {
        Expression = "x";
        switch (operator) {
            case Plus -> {
                Expression += " + ";
                Answer = String.valueOf((long) value_2 - value_1);
            }
            case Minus -> {
                Expression += " - ";
                Answer = String.valueOf((long) value_1 + value_2);
            }
            case Divide -> {
                Expression += " / ";
                Answer = String.valueOf((long) value_1 * value_2);
            }
            case Multiple -> {
                Expression += " * ";
                Answer = String.valueOf((long) value_2 / value_1);
            }
        }
        Expression += String.valueOf(value_1) + " = " + String.valueOf(value_2);
    }
}
