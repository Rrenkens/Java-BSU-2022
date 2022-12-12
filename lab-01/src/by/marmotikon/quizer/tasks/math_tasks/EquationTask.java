package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.tasks.Task;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    public static class EquationTaskGenerator extends AbstractMathTaskGenerator {
        /**
         * @param minNumber          минимальное число
         * @param maxNumber          максимальное число
         * @param generateOperations разрешить генерацию с данными операторами
         **/
        public EquationTaskGenerator (
                double minNumber,
                double maxNumber,
                EnumSet<Operation> generateOperations
        ) throws IllegalArgumentException {
            super(minNumber, maxNumber, 0, generateOperations);
            if (generateOperations == null || generateOperations.isEmpty()) {
                throw new IllegalArgumentException("can not generate any equation task without math operations");
            }
        }

        /**
         * @param minNumber          минимальное число
         * @param maxNumber          максимальное число
         * @param precision          количество знаков после запятой в генерируемых числах
         * @param generateOperations разрешить генерацию с данными операторами
         **/
        public EquationTaskGenerator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> generateOperations
        ) {
            super(minNumber, maxNumber, precision, generateOperations);
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            Number a = generateNumber();
            Number b = generateNumber();
            boolean isLeftX = random.nextBoolean();
            //noinspection OptionalGetWithoutIsPresent
            MathTask.Operation chosenOperation = generateOperations
                    .stream()
                    .skip(random.nextInt(generateOperations.size()))
                    .findFirst().get();

            String statement;
            Number answer;
            switch (chosenOperation) {
                case SUM -> {
                    if (isLeftX) {
                        statement = "x + " + a + " = " + b;
                    } else {
                        statement = a + " + x = " + b;
                    }
                    answer = new Number(b.getValue() - a.getValue(), precision);
                }
                case DIFFERENCE -> {
                    if (isLeftX) {
                        statement = "x - " + a + " = " + b;
                        answer = new Number(b.getValue() + a.getValue(), precision);
                    } else {
                        statement = a + " - x = " + b;
                        answer = new Number(a.getValue() - b.getValue(), precision);
                    }
                }
                case MULTIPLICATION -> {
                    while (a.isZero()) {
                        a = generateNumber();
                    }
                    if (isLeftX) {
                        statement = "x * " + a.toInt() + " = " + new Number(b.getValue() * a.toInt(), precision);
                    } else {
                        statement = a.toInt() + " * x = " + new Number(b.getValue() * a.toInt(), precision);
                    }
                    answer = new Number(b);
                }
                case DIVISION -> {
                    while (a.isZero()) {
                        a = generateNumber();
                    }
                    if (isLeftX) {
                        statement = "x / " + a + " = " + b.toInt();
                        answer = new Number(a.getValue() * b.toInt(), precision);
                    } else {
                        while (b.isZero()) {
                            b = generateNumber();
                        }
                        statement = new Number(a.getValue() * b.toInt(), precision)
                                + " / x = " + b.toInt();
                        answer = new Number(a);
                    }
                }
                default -> {
                    throw new RuntimeException("wtf operation enum has invalid values");
                }
            }
            return new EquationTask(statement, answer);
        }
    }

    public EquationTask(String statement, Number answer) {
        super(statement, answer);
    }

    @Override
    public Task copy() {
        return new EquationTask(super.statement, super.answer);
    }
}
