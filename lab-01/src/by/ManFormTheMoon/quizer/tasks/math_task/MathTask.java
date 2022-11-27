package by.ManFormTheMoon.quizer.tasks.math_task;

import by.ManFormTheMoon.quizer.tasks.Task;

import java.security.InvalidParameterException;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        int getMinNumber();

        int getMaxNumber();

        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        };
    }

    enum Operation {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE;
        public static Operation fromChar(char x) {
            switch(x) {
                case '+':
                    return PLUS;
                case '-':
                    return MINUS;
                case '/':
                    return DIVIDE;
                case '*' :
                    return MULTIPLY;
            }
            throw new InvalidParameterException();
        }
        public static Operation fromInt(int x) {
            switch(x) {
                case 0:
                    return PLUS;
                case 1:
                    return MINUS;
                case 2:
                    return DIVIDE;
                case 3:
                    return MULTIPLY;
            }
            throw new InvalidParameterException();
        }
        public static char toChar(Operation operation) {
            switch(operation) {
                case PLUS:
                    return '+';
                case MINUS:
                    return '-';
                case DIVIDE:
                    return '/';
                case MULTIPLY:
                    return '*';
            }
            throw new InvalidParameterException();
        }
    }
}
