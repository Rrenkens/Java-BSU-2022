package task_generators.math_task_generators;

import java.util.ArrayList;
import java.util.EnumSet;

abstract public class AbstractMathTaskGenerator implements MathTaskGenerator {

    AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<Operation> operations
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        accessibleSigns = new ArrayList<>();
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("maxNumber must be more or equal to minNumber");
        }
        if (operations.size() == 0) {
            throw new IllegalArgumentException("Operations size cant be empty");
        }
        accessibleSigns.addAll(operations);
    }

    char GetCharSign (Operation sign) {
        char sym = ' ';
        switch (sign) {
            case SUM -> sym = '+';
            case DIFFERENCE -> sym = '-';
            case MULTIPLICATION -> sym = '*';
            case DIVISION -> sym = '/';
        }
        return sym;
    }

    public int getMinNumber() {
        return minNumber;
    }// получить минимальное число
    public int getMaxNumber() {
        return maxNumber;
    }// получить максимальное число

    protected int minNumber;
    protected int maxNumber;

    protected ArrayList<Operation> accessibleSigns;
}
