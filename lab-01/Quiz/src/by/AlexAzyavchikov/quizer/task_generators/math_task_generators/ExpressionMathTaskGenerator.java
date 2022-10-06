package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.tasks.math_tasks.ExpressionMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class ExpressionMathTaskGenerator extends AbstractMathGenerator {
    public ExpressionMathTaskGenerator(double minNumber,
                                       double maxNumber,
                                       EnumSet<MathTask.Operator> operators) {
        this(minNumber, maxNumber, operators, 0);
    }

    public ExpressionMathTaskGenerator(double minNumber,
                                       double maxNumber,
                                       EnumSet<MathTask.Operator> operators,
                                       int precision) {
        InitializeFields(minNumber, maxNumber, operators, precision);
    }

    /**
     * return задание типа {@link ExpressionMathTask}
     */
    public ExpressionMathTask generate() {
        assert !operators.isEmpty();
        return new ExpressionMathTask(GenerateNumber(), GenerateOperator(), GenerateNumber(), precision);
    }
}
