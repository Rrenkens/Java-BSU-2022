package by.alexbiliba.quizer.tasks.math_tasks;

import by.alexbiliba.quizer.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class AbstractMathTask implements MathTask {

    static class Generator implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;
        protected int precision = 0;
        protected int answerPosition = 0;
        private int startPosition;
        private int boundaryForPosition;
        protected EnumSet<Operation> allowedOperations;

        /**
         * @param minNumber         минимальное число
         * @param maxNumber         максимальное число
         * @param allowedOperations разрешённые операторы для генерации (+, -, *, /)
         */
        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                int startPosition,
                int boundaryForPosition,
                EnumSet<MathTask.Operation> allowedOperations
        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            this.allowedOperations = allowedOperations;
        }

        public int getAnswerPosition() {
            return answerPosition;
        }

        public AbstractMathTask generate() {
            double leftNumber = ThreadLocalRandom.current().nextDouble(minNumber,
                    maxNumber);
            double rightNumber = ThreadLocalRandom.current().nextDouble(minNumber,
                    maxNumber);
            double resultNumber = ThreadLocalRandom.current().nextDouble(minNumber,
                    maxNumber);
            int operationIndex = ThreadLocalRandom.current().nextInt(0,
                    allowedOperations.size());

            int index = 0;
            MathTask.Operation operation = null;
            for (MathTask.Operation currentOperation : allowedOperations) {
                if (index == operationIndex) {
                    operation = currentOperation;
                    break;
                }
                index++;
            }

            answerPosition = ThreadLocalRandom.current().nextInt(startPosition,
                    boundaryForPosition + 1);

            if (operation == MathTask.Operation.SUM) {
                if (answerPosition == 0) {
                    leftNumber = resultNumber - rightNumber;
                } else if (answerPosition == 1) {
                    rightNumber = resultNumber - leftNumber;
                } else {
                    resultNumber = leftNumber + rightNumber;
                }
            } else if (operation == MathTask.Operation.DIFFERENCE) {
                leftNumber = resultNumber + rightNumber;
                if (answerPosition == 0) {
                    leftNumber = resultNumber + rightNumber;
                } else if (answerPosition == 1) {
                    rightNumber = leftNumber - resultNumber;
                } else {
                    resultNumber = leftNumber - rightNumber;
                }
            } else {
                while (rightNumber < 1e-10) {
                    rightNumber = ThreadLocalRandom.current().nextDouble(minNumber,
                            maxNumber);
                }
                if (operation == MathTask.Operation.MULTIPLICATION) {
                    leftNumber = resultNumber / rightNumber;
                    if (answerPosition == 0) {
                        leftNumber = resultNumber / rightNumber;
                    } else if (answerPosition == 1) {
                        rightNumber = resultNumber / leftNumber;
                    } else {
                        resultNumber = leftNumber * rightNumber;
                    }
                } else if (operation == MathTask.Operation.DIVISION) {
                    leftNumber = resultNumber * rightNumber;
                    if (answerPosition == 0) {
                        leftNumber = resultNumber * rightNumber;
                    } else if (answerPosition == 1) {
                        rightNumber = leftNumber / resultNumber;
                    } else {
                        resultNumber = leftNumber / rightNumber;
                    }
                }
            }

            return new AbstractMathTask(leftNumber, rightNumber, resultNumber, operation, precision, 2);
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
    }

    String text;
    String answer;

    protected double leftNumber = 0;
    protected double rightNumber = 0;
    protected double resultNumber = 0;
    protected MathTask.Operation operation = null;
    protected int precision = 0;
    private int answerPosition;

    public AbstractMathTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public AbstractMathTask(double leftNumber, double rightNumber, double resultNumber,
                            Operation operation, int precision, int answerPosition) {
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
        this.resultNumber = resultNumber;
        this.operation = operation;
        this.precision = precision;
        this.answerPosition = answerPosition;
        text = "";
        if (answerPosition == 0) {
            text += 'x';
            answer = BigDecimal.valueOf(leftNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        } else {
            text += BigDecimal.valueOf(leftNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        }
        text += operationSymbol.get(operation);

        if (answerPosition == 1) {
            text += 'x';
            answer = BigDecimal.valueOf(rightNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        } else {
            text += BigDecimal.valueOf(rightNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        }
        text += "=";

        if (answerPosition == 2) {
            text += '?';
            answer = BigDecimal.valueOf(resultNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        } else {
            text += String.format("%." + String.valueOf(precision) + "f", resultNumber);
            text += BigDecimal.valueOf(resultNumber)
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
        }
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    @Override
    public String getText() {
        return text;
    }

    public double getLeftNumber() {
        return leftNumber;
    }

    public double getRightNumber() {
        return rightNumber;
    }

    public double getResultNumber() {
        return resultNumber;
    }

    public int getPrecision() {
        return precision;
    }

    public MathTask.Operation getOperation() {
        return operation;
    }

    @Override
    public Result validate(String answer) {
        try {
            answer = BigDecimal.valueOf(Double.parseDouble(answer))
                    .setScale(precision, RoundingMode.HALF_UP)
                    .toString();
            double userAnswer = Double.parseDouble(answer);
            double rightAnswer = Double.parseDouble(this.answer);
            if (Math.abs(userAnswer - rightAnswer) < threshold) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    public String getAnswer() {
        return answer;
    }
}
