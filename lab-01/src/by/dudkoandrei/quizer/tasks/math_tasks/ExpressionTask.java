package by.dudkoandrei.quizer.tasks.math_tasks;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {

  public ExpressionTask(
      double firstNumber,
      double secondNumber,
      int precision,
      Operation operation) {
    super(operation.compute(firstNumber, secondNumber), precision);

    df.setMaximumFractionDigits(precision);
    df.setMinimumFractionDigits(precision);

    text = df.format(firstNumber) + " " + operation + " " + df.format(secondNumber) + " = ?";
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
    public ExpressionTask generate() {
      double firstNumber = truncate(rnd.nextDouble(minNumber, maxNumber), precision);
      double secondNumber = truncate(rnd.nextDouble(minNumber, maxNumber), precision);
      Operation operation = getRandomOperation();

      if (operation == Operation.DIVIDE
          && isDoubleEqual(secondNumber, 0.0, Math.pow(0.1, precision))) {
        operation = Operation.MULTIPLY;
      }

      return new ExpressionTask(firstNumber, secondNumber, precision, operation);
    }
  }
}
