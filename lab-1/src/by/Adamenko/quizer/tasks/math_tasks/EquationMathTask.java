package by.Adamenko.quizer.tasks.math_tasks;

public class EquationMathTask extends AbstractMathTask {
    public EquationMathTask(
            int value_1,
            int value_2,
            Operator operator
    ) {
        Expression = String.valueOf(value_1);
        switch (operator) {
            case Plus -> {
                Expression += " + ";
                Answer = String.valueOf((long) value_1 + value_2);
            }
            case Minus -> {
                Expression += " - ";
                Answer = String.valueOf((long) value_1 - value_2);
            }
            case Divide -> {
                Expression += " / ";
                Answer = String.valueOf((long) value_1 / value_2);
            }
            case Multiple -> {
                Expression += " * ";
                Answer = String.valueOf((long) value_1 * value_2);
            }
        }
        Expression += value_2 + "=?";
    }
}
