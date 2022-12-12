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
        if (isZero()) {
            return "0";
        }
        String string = Double.toString(value);
        int pos = string.length() - 1;
        while (string.charAt(pos) == '0') {
            pos--;
        }
        string = string.substring(0, pos);
        return string;
    }

    public Result provideAnswer(String answer) {
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

    public double getEpsilon() {
        return  epsilon;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public boolean isZero() {
        return Math.abs(value) < epsilon;
    }
}
