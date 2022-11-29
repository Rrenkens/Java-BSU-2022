package by.Savenok.quizer;

public class Randomizer {
    public static int GenerateNumber(int lower_bound, int upper_bound) {
        double psi = Math.random() / Math.nextDown(1.0);
        return (int) (lower_bound * (1.0 - psi) + upper_bound * psi);
    }

    public static char GenerateOperator(boolean sum,
                                        boolean difference,
                                        boolean multiply,
                                        boolean division) {
        char[] enable_operators = new char[4];
        enable_operators[0] = '0';
        enable_operators[1] = '0';
        enable_operators[2] = '0';
        enable_operators[3] = '0';
        if (sum) {
            enable_operators[0] = '+';
        }
        if (difference) {
            enable_operators[1] = '-';
        }
        if (multiply) {
            enable_operators[2] = '*';
        }
        if (division) {
            enable_operators[3] = '/';
        }
        char operator = '0';
        while (operator == '0') {
            int index = Randomizer.GenerateNumber(0, 4);
            operator = enable_operators[index];
        }
        return operator;
    }

    public enum XPosition {
        FIRST,
        SECOND
    }

    public static XPosition GetXPosition() {
        int pos = GenerateNumber(0, 2);
        if (pos == 0) {
            return XPosition.FIRST;
        }
        return XPosition.SECOND;
    }
}
