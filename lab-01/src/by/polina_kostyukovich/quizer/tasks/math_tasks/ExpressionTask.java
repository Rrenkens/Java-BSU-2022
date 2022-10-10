package by.polina_kostyukovich.quizer.tasks.math_tasks;

import by.polina_kostyukovich.quizer.Result;
import by.polina_kostyukovich.quizer.tasks.Task;

public class ExpressionTask extends AbstractMathTask {
    private final char operator;
    private final int answer;

    public ExpressionTask(int number1, int number2, Operation operation) {
        super(number1, number2);
        operator = getOperator(operation);
        answer = getAnswer(number1, number2, operation);
    }

    @Override
    public String getText() {
        return number1 + " " + operator + " " + number2 + " = ";
    }

    @Override
    public String getAnswer() {
        return String.valueOf(answer);
    }

    @Override
    public Result validate(String answer) {
        try {
            int answerNumber = Integer.parseInt(answer);
            return answerNumber == this.answer ? Result.OK : Result.WRONG;
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    private static int getAnswer(int number1, int number2, Operation operation) {
        switch (operation) {
            case SUM -> {
                return number1 + number2;
            }
            case DIFFERENCE -> {
                return number1 - number2;
            }
            case MULTIPLICATION -> {
                return number1 * number2;
            }
            case DIVISION -> {
                return number1 / number2;
            }
            default -> {
                return 0;
            }
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param operations             EnumSet с разрешенными операциями
         */
        public Generator(int minNumber, int maxNumber, Operation operation) {
            super(minNumber, maxNumber, operation);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        @Override
        public Task generate() {
            int number1 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int number2 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            return new ExpressionTask(number1, number2, operation);
        }
    }
}