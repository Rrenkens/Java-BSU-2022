package by.dudkoandrei.quizer.tasks.math_tasks;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {

  public EquationTask(
      double firstNumber,
      double secondNumber,
      int precision,
      boolean isFirstNumberUnknown,
      Operation operation) {
    super(operation.solve(firstNumber, secondNumber, isFirstNumberUnknown), precision);

    df.setMaximumFractionDigits(precision);
    df.setMinimumFractionDigits(precision);

    if (isFirstNumberUnknown) {
      text = "x " + operation + " " + df.format(firstNumber) + " = " + df.format(secondNumber);
    } else {
      text = df.format(firstNumber) + " " + operation + " x = " + df.format(secondNumber);
    }
  }

  public static class Generator extends AbstractMathTask.Generator {

    /**
     * @param minNumber  минимальное число
     * @param maxNumber  максимальное число
     * @param operations разрешённые операции
     */
    public Generator(
        double minNumber,
        double maxNumber,
        EnumSet<Operation> operations) {
      super(minNumber, maxNumber, operations);
    }

    /**
     * @param minNumber  минимальное число
     * @param maxNumber  максимальное число
     * @param precision  количество знаков после зяпятой в генерируемых числах
     * @param operations разрешённые операции
     */
    public Generator(
        double minNumber,
        double maxNumber,
        int precision,
        EnumSet<Operation> operations) {
      super(minNumber, maxNumber, precision, operations);
    }

    /**
     * @return задание типа {@link ExpressionTask}
     */
    @Override
    public EquationTask generate() {
      double eps = Math.pow(0.1, precision);

      double firstNumber = getRandomDouble();
      double secondNumber = getRandomDouble();
      boolean isFirstNumberUnknown = rnd.nextBoolean();
      Operation operation = getRandomOperation();

      if (operation == Operation.DIVIDE && isDoubleEqual(secondNumber, 0.0, eps)) {
        operation = Operation.MULTIPLY;
      }
      if (operation == Operation.MULTIPLY && isDoubleEqual(firstNumber, 0.0, eps)) {
        operation = Operation.ADD;
      }
      if (isFirstNumberUnknown && operation == Operation.DIVIDE
          && isDoubleEqual(firstNumber, 0.0, eps)) {
        operation = Operation.ADD;
      }
      if (!isFirstNumberUnknown && operation == Operation.DIVIDE
          && isDoubleEqual(secondNumber, 0.0, eps)) {
        operation = Operation.ADD;
      }

      return new EquationTask(firstNumber, secondNumber, precision, isFirstNumberUnknown,
          operation);
    }
  }
}
