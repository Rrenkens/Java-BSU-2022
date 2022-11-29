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
      switch (this) {
        case ADD -> {
          return lhs + rhs;
        }
        case SUBTRACT -> {
          return lhs - rhs;
        }
        case MULTIPLY -> {
          return lhs * rhs;
        }
        case DIVIDE -> {
          return lhs / rhs;
        }
        default -> {
          return 0;
          // exception
        }
      }
    }

    @Override
    public String toString() {
      switch (this) {
        case ADD -> {
          return "+";
        }
        case SUBTRACT -> {
          return "-";
        }
        case MULTIPLY -> {
          return "*";
        }
        case DIVIDE -> {
          return "/";
        }
        default -> {
          // exception
        }
      }
      return "";
    }

    double solve(double firstNumber, double secondNumber, boolean isFirstNumberUnknown) {
      switch (this) {
        case ADD -> {
          return secondNumber - firstNumber;
        }
        case MULTIPLY -> {
          return secondNumber / firstNumber;
        }
        case SUBTRACT -> {
          if (isFirstNumberUnknown) {
            return secondNumber + firstNumber;
          } else {
            return firstNumber - secondNumber;
          }
        }
        case DIVIDE -> {
          if (isFirstNumberUnknown) {
            return firstNumber * secondNumber;
          } else {
            return firstNumber / secondNumber;
          }
        }
        default -> {
          return 0;
          // exception
        }
      }
    }
  }
}
