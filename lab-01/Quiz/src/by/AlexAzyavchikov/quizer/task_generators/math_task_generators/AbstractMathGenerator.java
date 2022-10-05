package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.tasks.math_tasks.ExpressionMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public abstract class AbstractMathGenerator implements MathTaskGenerator {
    protected int minNumber;
    protected int maxNumber;

    protected EnumSet<MathTask.Operator> operators;
    ArrayList<MathTask.Operator> order = new ArrayList<MathTask.Operator>(List.of(new MathTask.Operator[]{
            MathTask.Operator.SUM, MathTask.Operator.DIFFERENCE, MathTask.Operator.MULTIPLICATION, MathTask.Operator.DIVISION
    }));

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    protected int GenerateNumber() {
        return (int) ((Math.random() * (maxNumber - minNumber)) + minNumber);
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
}
