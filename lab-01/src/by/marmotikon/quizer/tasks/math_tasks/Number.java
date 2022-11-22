package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.Result;

import java.text.DecimalFormat;

public class Number {
    private double value;
    private double epsilon;

    public Number(double value, int precision) {
        DecimalFormat decimalFormat = new DecimalFormat("#." + "#".repeat(precision));
        epsilon = Math.pow(10, -(precision + 1)) * 5;
        this.value = Double.parseDouble(decimalFormat.format(value));
    }

    public Number(Number other) {
        this.value = other.value;
        this.epsilon = other.epsilon;
    }

    public String toString() {
        String string = Double.toString(value);
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        if (string.endsWith(".")) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public Result equals(String answer) {
        try {
            if (Math.abs(value - Double.parseDouble(answer)) < epsilon) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    public int compareTo(Number other) {
        double diff = this.value - other.value;
        if (diff < -epsilon) {
            return -1;
        }
        if (diff > epsilon) {
            return 1;
        }
        return 0;
    }

    public void swap(Number other) {
        double cup = this.value;
        this.value = other.value;
        other.value = cup;
        cup = this.epsilon;
        this.epsilon = other.epsilon;
        other.epsilon = cup;
    }

    public int toInt() {
        return (int) value;
    }

    public double getValue() {
        return value;
    }

    public boolean isZero() {
        return value == 0;
    }
}
