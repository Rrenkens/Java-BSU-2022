package by.parfen01.quiser.task_generators.math_task_generators;

import by.parfen01.quiser.tasks.math_tasks.EquationTask;
import by.parfen01.quiser.tasks.math_tasks.MathTask;

import java.util.EnumSet;

class EquationTaskGenerator extends AbstractMathTaskGenerator {
    private final int[] possiblePositionsOfX;
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     */
     EquationTaskGenerator(int minNumber,
                           int maxNumber,
                           EnumSet<MathTask.Operation> operations,
                           int[] possiblePositionsOfX) {
         super(minNumber, maxNumber, operations);
         this.possiblePositionsOfX = possiblePositionsOfX.clone();
     }

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        int xPos = possiblePositionsOfX[(int)(Math.random() * possiblePositionsOfX.length)];
        return new EquationTask(getRandomNumberForTask(), getRandomNumberForTask(), xPos, getRandomOperationFromSet());
    }
}
