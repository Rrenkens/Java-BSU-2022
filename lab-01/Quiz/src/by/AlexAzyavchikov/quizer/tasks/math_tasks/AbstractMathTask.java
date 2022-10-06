package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.Result;
import by.AlexAzyavchikov.quizer.tasks.AbstractTask;

public abstract class AbstractMathTask extends AbstractTask implements MathTask {
    protected double answer;
    protected int precision;

    @Override
    public Result validate(String answer) {
        Result result;
        try {
            if (Double.compare(Round(Double.parseDouble(answer)), this.answer) == 0) {
                result = Result.OK;
            } else {
                result = Result.WRONG;
            }
        } catch (NumberFormatException e) {
            result = Result.INCORRECT_INPUT;
        }
        return result;
    }

    protected double Round(double value) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }
}
