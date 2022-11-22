package by.toharrius.quizer;

public enum MathOperation {
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY;

    public String toString() {
        return switch (this) {
            case ADD -> "+";
            case SUBTRACT -> "-";
            case DIVIDE -> "/";
            case MULTIPLY -> "*";
        };
    }

    public int eval(int a, int b) {
        return switch (this) {
            case ADD -> a + b;
            case SUBTRACT -> a - b;
            case DIVIDE -> a / b;
            case MULTIPLY -> a * b;
        };
    }

    public double eval(double a, double b) {
        return switch (this) {
            case ADD -> a + b;
            case SUBTRACT -> a - b;
            case DIVIDE -> a / b;
            case MULTIPLY -> a * b;
        };
    }
}
