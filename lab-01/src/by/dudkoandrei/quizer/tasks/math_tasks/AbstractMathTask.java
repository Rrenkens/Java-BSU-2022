package by.dudkoandrei.quizer.tasks.math_tasks;

import by.dudkoandrei.quizer.Result;
import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {

  protected final double answer;
  protected final double eps;
  protected String text;
  protected static final DecimalFormat df = new DecimalFormat();

  AbstractMathTask(double answer, int precision) {
    if (precision < 0) {
      throw new IllegalArgumentException("precision is negative");
    }

    this.answer = answer;
    this.eps = Math.pow(0.1, precision);
  }

  static boolean isDoubleEqual(double lhs, double rhs, double eps) {
    return Math.abs(lhs - rhs) < eps;
  }

  static double truncate(double number, int precision) {
    if (precision < 0) {
      throw new IllegalArgumentException("precision is negative");
    }

    double scale = Math.pow(10, precision);

    return Math.floor(number * scale) / scale;
  }

  @Override
  public Result validate(String answer) {
    double givenAnswer;

    try {
      givenAnswer = Double.parseDouble(answer);
    } catch (NumberFormatException e) {
      return Result.INCORRECT_INPUT;
    }

    if (isDoubleEqual(this.answer, givenAnswer, eps)) {
      return Result.OK;
    } else {
      return Result.WRONG;
    }
  }

  @Override
  public String getText() {
    return text;
  }

  abstract static class Generator implements MathTask.Generator {

    protected final double minNumber;
    protected final double maxNumber;
    protected final int precision;
    protected final EnumSet<Operation> allowedOperations;
    protected static final Random rnd = new Random();

    Generator(
        double minNumber,
        double maxNumber,
        EnumSet<Operation> operations) {
      this(minNumber, maxNumber, 0, operations);
    }

    Generator(
        double minNumber,
        double maxNumber,
        int precision,
        EnumSet<Operation> operations) {
      if (operations == null) {
        throw new IllegalArgumentException("operations is null");
      }
      if (operations.isEmpty()) {
        throw new IllegalArgumentException("operations is empty");
      }
      if (minNumber > maxNumber) {
        throw new IllegalArgumentException("minNumber is greater that maxNumber");
      }
      if (precision < 0) {
        throw new IllegalArgumentException("precision is negative");
      }

      this.minNumber = minNumber;
      this.maxNumber = maxNumber;
      this.allowedOperations = operations.clone();
      this.precision = precision;
    }

    double getRandomDouble() {
      try {
        return truncate(rnd.nextDouble(minNumber, maxNumber), precision);
      } catch (IllegalArgumentException e) {
        return truncate(minNumber, precision);
      }
    }

    Operation getRandomOperation() {
      int index = rnd.nextInt(allowedOperations.size());
      Iterator<Operation> iter = allowedOperations.iterator();

      while (index != 0) {
        iter.next();
        --index;
      }

      return iter.next();
    }

    @Override
    public double getMinNumber() {
      return minNumber;
    }

    @Override
    public double getMaxNumber() {
      return maxNumber;
    }

    @Override
    public int getPrecision() {
      return precision;
    }
  }
}
