package by.ZaharKalosha.quizzer.tasks.math_tasks;

import by.ZaharKalosha.quizzer.Result;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    private final String text;
    private final String answer;

    public ExpressionTask(double firstNumber, double secondNumber, int precision, Operation operation) {
        super(firstNumber, secondNumber, operation, precision);

        String preTaskOutput = "Необходима точность ответа не менее " + getDoublePrecision() + "\n";
        preTaskOutput += firstNumber + " " + AbstractMathTask.getCharOperator(operation) + " " + secondNumber + " = ";
        text = preTaskOutput;

        if (secondNumber == 0 && operation == Operation.DIVISION) {
            answer = "Некорректная операция";
            return;
        }

        answer = String.valueOf(calculate(firstNumber, secondNumber, operation));
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new NullPointerException();
        }

        try {
            double givenAnswer = Double.parseDouble(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        return checkAnswer(answer, this.answer);
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber минимальное число
         * @param maxNumber максимальное число
         */
        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, precision, operations);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            this(minNumber, maxNumber, 2, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            return new ExpressionTask(getRandomNumberForTask(), getRandomNumberForTask(),
                                      getPrecision(), getRandomOperation());
        }
    }
}
