package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.Task;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.EquationMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;

public class EquationMathTaskGenerator extends AbstractMathGenerator {
    public EquationMathTaskGenerator(double minNumber,
                                     double maxNumber,
                                     EnumSet<MathTask.Operator> operators) {
        this(minNumber, maxNumber, operators, 0);
    }

    public EquationMathTaskGenerator(double minNumber,
                                     double maxNumber,
                                     EnumSet<MathTask.Operator> operators,
                                     int precision) {
        InitializeFields(minNumber, maxNumber, operators, precision);
    }

    /**
     * return задание типа {@link EquationMathTask}
     */
    public EquationMathTask generate() {
        assert !operators.isEmpty();
        return new EquationMathTask(GenerateNumber(), GenerateOperator(), GenerateNumber(), precision);
    }

}
