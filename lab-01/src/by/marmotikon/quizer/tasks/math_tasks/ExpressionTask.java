package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.tasks.Task;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    public static class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateOperations     разрешить генерацию с данными операторами
         */
        public ExpressionTaskGenerator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> generateOperations
        ) throws IllegalArgumentException {
            super(minNumber, maxNumber, 0, generateOperations);
            if (generateOperations == null || generateOperations.isEmpty()) {
                throw new IllegalArgumentException("can not generate any expression task without math operations");
            }
        }

        /**
         * @param minNumber          минимальное число
         * @param maxNumber          максимальное число
         * @param precision          количество знаков после запятой в генерируемых числах
         * @param generateOperations разрешить генерацию с данными операторами
         **/
        public ExpressionTaskGenerator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> generateOperations
        ) {
            super(minNumber, maxNumber, precision, generateOperations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            Number a = generateNumber();
            Number b = generateNumber();
            //noinspection OptionalGetWithoutIsPresent
            MathTask.Operation chosenOperation = generateOperations
                    .stream()
                    .skip(random.nextInt(generateOperations.size()))
                    .findFirst().get();
            String statement;
            Number answer;
            switch (chosenOperation) {
                case SUM -> {
                    statement = a + " + " + b + " = ?";
                    answer = new Number(a.getValue() + b.getValue(), precision);
                }
                case DIFFERENCE -> {
                    statement = a + " - " + b + " = ?";
                    answer = new Number(a.getValue() - b.getValue(), precision);
                }
                case MULTIPLICATION -> {
                    statement = a.toInt() + " * " + b + " = ?";
                    answer = new Number(b.getValue() * a.toInt(), precision);
                }
                case DIVISION -> {
                    while (b.isZero()) {
                        b = generateNumber();
                    }
                    statement = new Number(a.getValue() * b.toInt(), precision) + " / " + b.toInt() + " = ?";
                    answer = new Number(a);
                }
                default -> throw new RuntimeException("wtf operation enum has invalid values");
            }
            return new ExpressionTask(statement, answer);
        }
    }

    public ExpressionTask(String statement, Number answer) {
        super(statement, answer);
    }

    @Override
    public Task copy() {
        return new ExpressionTask(super.statement, super.answer);
    }
}
