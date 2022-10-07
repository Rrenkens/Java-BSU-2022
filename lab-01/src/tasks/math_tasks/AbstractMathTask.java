package tasks.math_tasks;

import by.DaniilDomnin.quizer.MathTask;
import by.DaniilDomnin.quizer.Result;

abstract public class AbstractMathTask implements MathTask {
    public AbstractMathTask (String text, int answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        try {
            if (this.answer == Integer.parseInt(answer)) {
                return Result.OK;
            }
            return Result.WRONG;
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    protected String text;
    protected int answer;
}
