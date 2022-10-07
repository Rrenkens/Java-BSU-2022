package task_generators.math_task_generators;

import tasks.math_tasks.EquationTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class EquationTaskGenerator extends AbstractMathTaskGenerator {
    public EquationTaskGenerator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
        super(minNumber, maxNumber, operations);
    }

    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        Random ran = new Random();
        int first_num = ran.nextInt(min_number, max_number + 1);
        int second_num = ran.nextInt(min_number, max_number + 1);
        int index = ran.nextInt(0, accessible_signs.size());
        XPosition pos;
        if (ran.nextInt(0, 2)  == 0) {
            pos = XPosition.LEFT;
        } else {
            pos = XPosition.RIGHT;
        }
        if (accessible_signs.get(index) == Operation.MULTIPLICATION || (accessible_signs.get(index) == Operation.DIVISION && pos == XPosition.RIGHT)) {
           if (second_num < first_num) {
               int tmp = first_num;
               first_num = second_num;
               second_num = tmp;
           }

           if (first_num == 0) {
               first_num = max_number;
           }

           if (second_num % first_num != 0) {
               ArrayList<Integer> dividers = new ArrayList<>();
               for (int i = min_number; i <= Math.min(max_number, second_num); ++i) {
                   if (second_num % i == 0) {
                       dividers.add(i);
                   }
               }
               first_num = dividers.get(ran.nextInt(0, dividers.size()));
           }
        }
        int answer = GenerateAnswer(first_num, second_num, accessible_signs.get(index), pos);
        String string_answer;
        if (pos == XPosition.RIGHT) {
             string_answer = String.valueOf(first_num) + String.valueOf(GetCharSign(accessible_signs.get(index))) + "x=" + String.valueOf(second_num);
        } else {
             string_answer = "x" + String.valueOf(GetCharSign(accessible_signs.get(index))) + String.valueOf(first_num) + "=" + String.valueOf(second_num);
        }
        return new EquationTask(string_answer, answer);
    }

    int GenerateAnswer (int a, int b, Operation sign, XPosition pos) {
        int answer = 0;
        if (sign == Operation.SUM) {
            answer = b- a;
        } else if (sign == Operation.DIFFERENCE) {
            if (pos == XPosition.RIGHT) {
                answer = a - b;
            } else {
                answer = a + b;
            }
        } else if (sign == Operation.DIVISION) {
            if (pos == XPosition.LEFT) {
                answer = b * a;
            } else {
                answer = a / b;
            }
        } else {
            answer = b / a;
        }
        return answer;
    }

    public enum XPosition {
        LEFT,
        RIGHT
    }

    class IntPair {
        private int a, b;
        public IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int first() {
            return a;
        }

        public int second() {
            return b;
        }
    }

}




