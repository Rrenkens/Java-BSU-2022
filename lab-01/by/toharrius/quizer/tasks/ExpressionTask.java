package by.toharrius.quizer.tasks;

import by.toharrius.quizer.MathOperation;
import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;

public class ExpressionTask implements Task {
    private final int a;
    private final MathOperation operation;
    private final int b;
    private final int result;

    public ExpressionTask(int a, MathOperation operation, int b) {
        this.a = a;
        this.operation = operation;
        this.b = b;
        this.result = operation.eval(a, b);
    }

    @Override
    public String getText() {
        return String.valueOf(a) + ' ' + operation + ' ' + b + " = ";
    }

    @Override
    public Result validate(String answer) {
        try {
            if (Integer.parseInt(answer) == result) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }
}
