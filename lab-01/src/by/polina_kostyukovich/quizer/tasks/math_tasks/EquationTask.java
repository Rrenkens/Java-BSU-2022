package by.polina_kostyukovich.quizer.tasks.math_tasks;

import by.polina_kostyukovich.quizer.Result;
import by.polina_kostyukovich.quizer.tasks.Task;

public class EquationTask extends AbstractMathTask {
    private final char operator;
    private final int answer;
    private final boolean isXOnFirstPosition;

    public EquationTask(int number1, int number2, Operation operation, boolean isXOnFirstPosition) {
        super(number1, number2);
        operator = getOperator(operation);
        answer = getAnswer(number1, number2, operation, isXOnFirstPosition);
        this.isXOnFirstPosition = isXOnFirstPosition;
    }

    @Override
    public String getText() {
        if (isXOnFirstPosition) {
            return "x " + operator + " " + number1 + " = " + number2 + "\nx = ";
        } else {
            return number1 + " " + operator + " x = " + number2 + "\nx = ";
        }
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

    private static int getAnswer(int number1, int number2, Operation operation, boolean isXOnFirstPosition) {
        switch (operation) {
            case SUM -> {
                return number2 - number1;
            }
            case DIFFERENCE -> {
                return isXOnFirstPosition ? number1 + number2 : number1 - number2;
            }
            case MULTIPLICATION -> {
                return number2 / number1;
            }
            case DIVISION -> {
                return isXOnFirstPosition ? number1 * number2 : number1 / number2;
            }
            default -> {
                return 0;
            }
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final boolean isXOnFirstPosition;

        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param operations             EnumSet с разрешенными операциями
         * @param isXOnFirstPosition     стоит ли х по левую сторону оператора в уравнении
         */
        public Generator(int minNumber, int maxNumber, Operation operation, boolean isXOnFirstPosition) {
            super(minNumber, maxNumber, operation);
            this.isXOnFirstPosition = isXOnFirstPosition;
        }

        /**
         * return задание типа {@link EquationTask}
         */
        @Override
        public Task generate() {
            int number1 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int number2 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            return new EquationTask(number1, number2, operation, isXOnFirstPosition);
        }
    }
}
