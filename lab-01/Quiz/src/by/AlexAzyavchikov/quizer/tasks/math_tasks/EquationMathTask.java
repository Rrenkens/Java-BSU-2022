package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.exceptions.IncorrectInputException;

import java.util.List;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    private enum EquationType {
        Standard,
        Reverse;
        private static final List<EquationMathTask.EquationType> EQUATION_TYPES = List.of(values());
        private static final int SIZE = EQUATION_TYPES.size();
        private static final Random RANDOM = new Random();

        public static EquationMathTask.EquationType randomLetter() {
            return EQUATION_TYPES.get(RANDOM.nextInt(SIZE));
        }
    }

    public EquationMathTask(double num1, Operator operator, double num2) {
        this(num1, operator, num2, 0);
    }

    public EquationMathTask(double num1, Operator operator, double num2, int precision) {
        if (precision < 0) {
            throw new IncorrectInputException("In EquationMathTask precision(" + precision + ") < 0");
        }
        this.precision = precision;
        switch (EquationMathTask.EquationType.randomLetter()) {
            case Standard -> {
                //num1<operator>x=num2
                text = MathTask.ValueInBraces(num1) + MathTask.OperatorsSymbol(operator) + "x=" + MathTask.ValueInBraces(num2);
                switch (operator) {
                    case DIFFERENCE -> {
                        answer = num1 - num2;
                    }
                    case DIVISION -> {
                        answer = num1 / num2;
                    }
                    default -> {
                        answer = MathTask.MakeReverseOperation(num2, operator, num1);
                    }
                }
            }
            case Reverse -> {
                //x<operator>num1=num2
                text = "x" + MathTask.OperatorsSymbol(operator) + MathTask.ValueInBraces(num1) + "=" + MathTask.ValueInBraces(num2);
                answer = MathTask.MakeReverseOperation(num2, operator, num1);
            }
        }
        answer = Round(answer);
    }
}
