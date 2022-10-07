package by.parfen01.quiser.task_generators.math_task_generators;

import by.parfen01.quiser.TaskGenerator;
import by.parfen01.quiser.tasks.math_tasks.MathTask;
import java.util.EnumSet;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber(); // получить минимальное число
    int getMaxNumber(); // получить максимальное число
    EnumSet<MathTask.Operation> getOperations();
    /**
     * @return разница между максимальным и минимальным возможным числом
     */
    default int getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }

    default int getRandomNumberForTask() {
        return (int)(Math.random() * getDiffNumber() + getMinNumber());
    }

    default MathTask.Operation getRandomOperationFromSet() {
        while (true) {
            MathTask.Operation operationToUse = MathTask.Operation.fromInt((int)(Math.random() * 4));
            if (getOperations().contains(operationToUse)) {
                return operationToUse;
            }
        }
    }
}
