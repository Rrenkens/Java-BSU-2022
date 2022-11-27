package by.dudkoandrei.quizer.tasks.math_tasks;

import by.dudkoandrei.quizer.Result;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {

  protected final double answer;
  protected final int precision;
  protected final double eps;

  AbstractMathTask(double answer, int precision) {
    if (precision < 0) {
      // exception
    }

    this.answer = answer;
    this.precision = precision;
    this.eps = Math.pow(0.1, precision);
  }

  @Override
  public Result validate(String answer) {
    double givenAnswer;

    try {
      givenAnswer = Double.parseDouble(answer);
    } catch (NumberFormatException e) {
      return Result.INCORRECT_INPUT;
    }

    if (Math.abs(givenAnswer - this.answer) < eps) {
      return Result.OK;
    } else {
      return Result.WRONG;
    }
  }

  abstract static class Generator implements MathTask.Generator {

    protected final double minNumber;
    protected final double maxNumber;
    protected final int precision;
    protected final EnumSet<Operation> allowedOperations;

    Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
      if (operations == null) {
        // exception
      }
      if (operations.isEmpty()) {
        // exception
      }
      if (minNumber > maxNumber) {
        // exception
      }

      this.minNumber = minNumber;
      this.maxNumber = maxNumber;
      this.allowedOperations = operations.clone();
      this.precision = 0;
    }

    Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> operations) {
      if (operations == null) {
        // exception
      }
      if (operations.isEmpty()) {
        // exception
      }
      if (minNumber > maxNumber) {
        // exception
      }
      if (precision < 0) {
        // exception
      }

      this.minNumber = minNumber;
      this.maxNumber = maxNumber;
      this.allowedOperations = operations.clone();
      this.precision = precision;
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
