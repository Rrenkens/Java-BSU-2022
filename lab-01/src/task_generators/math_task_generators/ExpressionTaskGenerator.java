package task_generators.math_task_generators;

import tasks.math_tasks.ExpressionTask;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {

    public ExpressionTaskGenerator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
        super(minNumber, maxNumber, operations);
    }

    public ExpressionTask generate() {
        Random ran = new Random();
        int first_num = ran.nextInt(minNumber, maxNumber + 1);
        int second_num = ran.nextInt(minNumber, maxNumber + 1);
        int index = ran.nextInt(0, accessibleSigns.size());
        int answer = GenerateAnswer(first_num, second_num, accessibleSigns.get(index));
        String string_answer = first_num + String.valueOf(GetCharSign(accessibleSigns.get(index))) + second_num + "=?";
        return new ExpressionTask(string_answer, answer);
    }


    int GenerateAnswer (int a, int b, Operation sign) {
        int answer = 0;
        switch (sign) {
            case SUM -> answer = a + b;
            case DIFFERENCE -> answer = a - b;
            case MULTIPLICATION -> answer = a * b;
            case DIVISION -> answer = a / b;
        }
        return answer;
    }
}
