package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.exceptions.IncorrectInputException;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public abstract class AbstractMathGenerator implements MathTaskGenerator {
    protected double minNumber;
    protected double maxNumber;
    protected int precision;

    protected ArrayList<MathTask.Operator> operators;

    public double getMinNumber() {
        return minNumber;
    }

    public double getMaxNumber() {
        return maxNumber;
    }

    public int getPrecision() {
        return precision;
    }

    public AbstractMathGenerator(double minNumber,
                                 double maxNumber,
                                 EnumSet<MathTask.Operator> operators,
                                 int precision) {
        this.minNumber = MathTask.Round(minNumber, getPrecision());
        this.maxNumber = MathTask.Round(maxNumber, getPrecision());
        if (precision < 0) {
            throw new IncorrectInputException("In EquationMathTask precision(" + precision + ") < 0");
        }
        this.precision = precision;
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("In ExpressionMathTaskGenerator maxNumber(" + maxNumber
                    + ") < minNumber(" + minNumber + ") !");
        }
        this.operators = new ArrayList<>(operators);
    }

    protected double GenerateNumber() {
        double randomValue = (Math.random() * getDiffNumber()) + getMinNumber();
        return MathTask.Round(randomValue, getPrecision());
    }

    protected MathTask.Operator GenerateOperator() {
        assert !operators.isEmpty();
        Collections.shuffle(operators);
        return operators.get(0);
    }
}
