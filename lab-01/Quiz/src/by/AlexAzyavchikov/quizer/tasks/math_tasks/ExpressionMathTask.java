package by.AlexAzyavchikov.quizer.tasks.math_tasks;

public class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(double num1, Operator operator, double num2) {
        this(num1, operator, num2, 0);
    }

    public ExpressionMathTask(double num1, Operator operator, double num2, int precision) {
        this.precision = precision;
        text = MathTask.ValueInBraces(num1) + MathTask.OperatorsSymbol(operator) + MathTask.ValueInBraces(num2) + "=?";
        answer = Round(MathTask.MakeOperation(num1, operator, num2));
    }
}
