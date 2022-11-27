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
  }
}
