package by.Adamenko.quizer.tasks.math_tasks;

import by.Adamenko.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected String Expression;
    protected String Answer;
    @Override
    public String getText() {
        return Expression;
    }

    @Override
    public Result validate(String answer) {
        for (int i = 0; i < answer.length(); ++i) {
            if (!Character.isDigit(answer.charAt(i))) {
                if (i == 0 && answer.charAt(i) == '-') {
                    continue;
                }
                return Result.INCORRECT_INPUT;
            }
        }
        if (answer.equals(Answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
