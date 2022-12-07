package by.dudkoandrei.quizer.tasks.math_tasks;

import by.dudkoandrei.quizer.tasks.Task;

public interface MathTask extends Task {

  interface Generator extends Task.Generator {

    /**
     * @return минимальное число
     */
    double getMinNumber();

    /**
     * @return максимальное число
     */
    double getMaxNumber();

    /**
     * @return количество знаков после запятой в генерируемых числах
     */
    int getPrecision();

    /**
     * @return разница между максимальным и минимальным возможным числом
     */
    default double getDiffNumber() {
      return getMaxNumber() - getMinNumber();
    }
  }

  /**
   * Enum, который описывает допустимые операции для {@link MathTask}.
   */
  enum Operation {
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY;

    double compute(double lhs, double rhs) {
      return switch (this) {
        case ADD -> lhs + rhs;
        case SUBTRACT -> lhs - rhs;
        case MULTIPLY -> lhs * rhs;
        case DIVIDE -> lhs / rhs;
      };
    }

    @Override
    public String toString() {
      return switch (this) {
        case ADD -> "+";
        case SUBTRACT -> "-";
        case MULTIPLY -> "*";
        case DIVIDE -> "/";
      };
    }

    double solve(double firstNumber, double secondNumber, boolean isFirstNumberUnknown) {
      return switch (this) {
        case ADD -> secondNumber - firstNumber;
        case MULTIPLY -> secondNumber / firstNumber;
        case SUBTRACT ->
            isFirstNumberUnknown ? secondNumber + firstNumber : firstNumber - secondNumber;
        case DIVIDE ->
            isFirstNumberUnknown ? firstNumber * secondNumber : firstNumber / secondNumber;
      };
    }
  }
}
