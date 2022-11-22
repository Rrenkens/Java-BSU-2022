package by.marmotikon.quizer.tasks.math_tasks;

import java.util.EnumSet;

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
            super(minNumber, maxNumber, 0, EnumSet.allOf(MathTask.Operation.class));
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
}
