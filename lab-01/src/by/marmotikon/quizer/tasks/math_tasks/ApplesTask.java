package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.tasks.Task;

public class ApplesTask extends AbstractMathTask{
    public static class ApplesTaskGenerator extends AbstractMathTaskGenerator {

        /**
         * @param minNumber          минимальное число
         * @param maxNumber          максимальное число
         **/
        public ApplesTaskGenerator(
                double minNumber,
                double maxNumber
        ) {
            super(minNumber, maxNumber, 0, null);
        }
        /**
         * return задание типа {@link ApplesTask}
         */
        public ApplesTask generate() {
            Number a = generateNumber();
            Number b = generateNumber();
            if (a.compareTo(b) < 0) {
                a.swap(b);
            }
            return new ApplesTask("У A было " + a + " яблок, он(она) подарил(а) B "
                    + b + " яблок. Сколько яблок осталось у A?",
                    new Number(a.getValue() - b.getValue(), precision));
        }

    }
    public ApplesTask(String statement, Number answer) {
        super(statement, answer);
    }

    @Override
    public Task copy() {
        return new ApplesTask(super.statement, super.answer);
    }
}
