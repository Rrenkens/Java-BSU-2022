package by.toharrius.quizer.tasks.math_tasks;

import by.toharrius.quizer.Task;
import by.toharrius.quizer.TaskGenerator;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public interface MathTask extends Task {
    interface Generator extends TaskGenerator {
        int getMinNumber();
        int getMaxNumber();
        EnumSet<MathOperation> getAllowed();
        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber() + 1;
        }
        default int generateOperand() {
            var r = ThreadLocalRandom.current();
            return getMinNumber() + r.nextInt(getDiffNumber());
        }
        default MathOperation generateMathOperation() {
            var r = ThreadLocalRandom.current();
            var stream = getAllowed().stream();
            int index = r.nextInt((int)stream.count());
            return stream.skip(index).findFirst().get();
        }
    }
}
