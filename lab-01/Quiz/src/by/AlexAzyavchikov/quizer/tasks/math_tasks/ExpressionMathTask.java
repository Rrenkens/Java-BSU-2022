package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.Result;

public class ExpressionMathTask extends AbstractMathTask {
    private String text;
    int answer;

    public ExpressionMathTask(int num1, Operator operator, int num2) {
        text = String.valueOf(num1) + MathTask.OperatorsSymbol(operator) + String.valueOf(num2) + "=?";
        answer = MathTask.MakeOperation(num1, operator, num2);
    }

    @Override
    public Result validate(String answer) {
        Result result;
        try {
            if (Integer.parseInt(answer) == this.answer) {
                result = Result.OK;
            } else {
                result = Result.WRONG;
            }
        } catch (NumberFormatException e) {
            result = Result.INCORRECT_INPUT;
        }
        return result;
    }
}
