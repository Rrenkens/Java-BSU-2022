package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public abstract class AbstractMathGenerator implements MathTaskGenerator {
    protected double minNumber;
    protected double maxNumber;
    protected int precision;

    protected EnumSet<MathTask.Operator> operators;
    ArrayList<MathTask.Operator> order = new ArrayList<MathTask.Operator>(List.of(new MathTask.Operator[]{
            MathTask.Operator.SUM, MathTask.Operator.DIFFERENCE, MathTask.Operator.MULTIPLICATION, MathTask.Operator.DIVISION
    }));

    public double getMinNumber() {
        return minNumber;
    }

    public double getMaxNumber() {
        return maxNumber;
    }

    public int getPrecision() {
        return precision;
    }

    protected double GenerateNumber() {
        double randomValue = (Math.random() * getDiffNumber()) + getMinNumber();
        return MathTask.Round(randomValue, getPrecision());
    }

    protected MathTask.Operator GenerateOperator() {
        assert !operators.isEmpty();
        Collections.shuffle(order);
        for (MathTask.Operator operator : order) {
            if (operators.contains(operator)) {
                return operator;
            }
        }
        return null;
    }

    protected void InitializeFields(double minNumber,
                                    double maxNumber,
                                    EnumSet<MathTask.Operator> operators,
                                    int precision) {
        this.minNumber = MathTask.Round(minNumber, precision);
        this.maxNumber = MathTask.Round(maxNumber, precision);
        this.precision = precision;
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("In ExpressionMathTaskGenerator maxNumber(" + String.valueOf(maxNumber)
                    + ") < minNumber(" + String.valueOf(minNumber) + ") !");
        }
        if (operators.isEmpty()) {
            assert false;
//            TODO: throw exception
        }
        this.operators = operators;
    }
}
