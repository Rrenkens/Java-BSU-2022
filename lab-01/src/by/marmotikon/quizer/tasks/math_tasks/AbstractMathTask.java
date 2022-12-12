package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.Result;
import by.marmotikon.quizer.tasks.Task;

import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {
    public abstract static class AbstractMathTaskGenerator implements MathTaskGenerator {
        private final double minNumber;
        private final double maxNumber;
        protected final int precision;
        protected final EnumSet<Operation> generateOperations;
        protected final Random random = new Random();

        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param precision              количество знаков после запятой в генерируемых числах
         * @param generateOperations     разрешить генерацию с данными операторами
         **/
        public AbstractMathTaskGenerator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<MathTask.Operation> generateOperations
        ) throws IllegalCallerException {
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("can not generate any math task with minNumber > maxNumber");
            }
            if (precision < 0) {
                throw new IllegalArgumentException("can not generate any math task with invalid precision < 0");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            this.generateOperations = generateOperations;
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        protected Number generateNumber() {
            return new Number(getDiffNumber() * random.nextDouble() + getMinNumber(), precision);
        }

    }

    protected final String statement;
    protected final Number answer;


    /**
     * @param statement   текст задания
     * @param answer ответ на задание
     * @see Number
     */
    public AbstractMathTask(String statement, Number answer) {
        this.statement = statement;
        this.answer = answer;
    }

    /**
     * @param other копируемое задание
     */
    public AbstractMathTask(AbstractMathTask other) {
        this.statement = other.statement;
        this.answer = other.answer;
    }

    @Override
    public String getText() {
        return statement;
    }

    @Override
    public String getAnswerString() {
        return String.valueOf(answer);
    }

    public Number getAnswer() {
        return answer;
    }

    @Override
    public Result validate(String answer) {
       return this.answer.provideAnswer(answer);
    }

    @Override
    public boolean equals(Task other) {
        if (this == other) return true;
        if (!(other instanceof AbstractMathTask otherMathTask)) return false;
        return statement.equals(other.getText()) && answer.compareTo(otherMathTask.getAnswer()) == 0;
    }
}
