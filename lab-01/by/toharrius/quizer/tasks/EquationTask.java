package by.toharrius.quizer.tasks;

import by.toharrius.quizer.MathOperation;
import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;

public class EquationTask extends TextTask implements Task {
    public EquationTask(MathOperation op, int constant, int answer, int x) {
        super("x " + op + " " + constant + " = " + answer, String.valueOf(x));
    }

    public EquationTask(int constant, MathOperation op, int answer, int x) {
        super(String.valueOf(constant) + op + " x = " + answer, String.valueOf(x));
    }
}
