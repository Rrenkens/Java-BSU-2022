package task_generators.math_task_generators;

import java.util.ArrayList;
import java.util.EnumSet;

abstract public class AbstractMathTaskGenerator implements MathTaskGenerator {

    AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<Operation> operations
    ) {
        min_number = minNumber;
        max_number = maxNumber;
        accessible_signs = new ArrayList<>();
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("maxNumber must be more or equal to minNumber");
        }
        if (operations.size() == 0) {
            throw new IllegalArgumentException("Operations size cant be empty");
        }
        accessible_signs.addAll(operations);
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
        return min_number;
    }// получить минимальное число
    public int getMaxNumber() {
        return max_number;
    }// получить максимальное число

    protected int min_number;
    protected int max_number;

    protected ArrayList<Operation> accessible_signs;
}
