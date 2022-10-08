package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.exceptions.IncorrectInputException;

public class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(double num1, Operator operator, double num2) {
        this(num1, operator, num2, 0);
    }

    public ExpressionMathTask(double num1, Operator operator, double num2, int precision) {
        super(precision);
        if ((operator.equals(Operator.SUM) || operator.equals(Operator.MULTIPLICATION)) && num2 > num1) {
            double temp = num2;
            num2 = num1;
            num1 = temp;
        }
        text = ValueInBraces(num1) + operator.symbol() + ValueInBraces(num2) + "=?";
        answer = Round(operator.makeOperation(num1, num2));
    }
}
