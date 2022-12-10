package by.toharrius.quizer.tasks.math_tasks;

import by.toharrius.quizer.tasks.Task;
import by.toharrius.quizer.task_generators.TaskGenerator;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public interface MathTask extends Task {
    interface Generator extends TaskGenerator {
        double getMinNumber();
        double getMaxNumber();
        double getRoundingCoefficient();
        EnumSet<MathOperation> getAllowed();
        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
        default double generateOperand() {
            var generated = getMinNumber() + Math.random() * getDiffNumber();
            return Math.round(generated
                    * getRoundingCoefficient())
                    / getRoundingCoefficient();
        }
        default MathOperation generateMathOperation() {
            var r = ThreadLocalRandom.current();
            int index = r.nextInt(getAllowed().size());
            return getAllowed().stream().skip(index).findFirst().get();
        }
    }
}
