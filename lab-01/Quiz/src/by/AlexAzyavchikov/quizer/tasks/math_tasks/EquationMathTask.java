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
        super(precision);
        switch (operator) {
            case DIFFERENCE, DIVISION -> {
                switch (EquationMathTask.EquationType.randomLetter()) {
                    case Standard -> {
                        //num1<operator>x=num2
                        text = ValueInBraces(num1) + operator.symbol() + "x=" + ValueInBraces(num2);
                        answer = operator.makeOperation(num1, num2);
                    }
                    case Reverse -> {
                        //x<operator>num1=num2
                        text = "x" + operator.symbol() + ValueInBraces(num1) + "=" + ValueInBraces(num2);
                        answer = operator.makeReverseOperation(num2, num1);
                    }
                }
            }
            default -> {
                if (num2 > num1) {
                    double temp = num2;
                    num2 = num1;
                    num1 = temp;
                }
                text = text = ValueInBraces(num1) + operator.symbol() + "x=" + ValueInBraces(num2);
                answer = operator.makeReverseOperation(num2, num1);
            }
        }
        answer = Round(answer);
    }
}
