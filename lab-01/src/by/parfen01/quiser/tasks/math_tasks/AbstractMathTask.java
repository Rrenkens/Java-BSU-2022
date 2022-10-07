package by.parfen01.quiser.tasks.math_tasks;

public abstract class AbstractMathTask implements MathTask {
    private int firstNumber;
    private int secondNumber;
    private Operation operation;

    AbstractMathTask(int firstNumber, int secondNumber, Operation operation) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
    }
}
