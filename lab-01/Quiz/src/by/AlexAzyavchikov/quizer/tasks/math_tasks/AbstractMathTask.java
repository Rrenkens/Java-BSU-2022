package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.Result;
import by.AlexAzyavchikov.quizer.exceptions.IncorrectInputException;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.TextTask;

public abstract class AbstractMathTask implements MathTask {
    protected String text;
    protected double answer;
    protected int precision;

    public AbstractMathTask(int precision) {
        if (precision < 0) {
            throw new IncorrectInputException("In AbstractMathTask precision(" + precision + ") < 0");
        }
        this.precision = precision;
    }

    @Override
    public Result validate(String answer) {
        Result result;
        try {
            double parsed_answer = Round(Double.parseDouble(answer));
            if (Double.compare(parsed_answer, this.answer) == 0 ||
                    (Double.isNaN(parsed_answer) && Double.isNaN(this.answer))) {
                result = Result.OK;
            } else {
                result = Result.WRONG;
            }
        } catch (NumberFormatException e) {
            result = Result.INCORRECT_INPUT;
        }
        return result;
    }

    public int getPrecision() {
        return precision;
    }

    public String getText() {
        return text;
    }

    static String ValueInBraces(double value) {
        return "(" + value + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractMathTask)) {
            return false;
        }
        return this.text.equals(((Task) o).getText());
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
