package by.Savenok.quizer;

import by.Savenok.quizer.task_generators.TaskGenerator;
import by.Savenok.quizer.task_generators.math_task_generators.EquationTaskGenerator;
import by.Savenok.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.Savenok.quizer.task_generators.TextTaskGenerator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    static Map<String, Quiz> getQuizMap() throws FileNotFoundException {
        Map<String, Quiz> generators = new HashMap<>();
        generators.put("Expression mode (5 tasks)",
                new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 5));
        generators.put("Expression mode (10 tasks)",
                new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 10));
        generators.put("Expression mode (15 tasks)",
                new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 15));
        generators.put("Equation mode (5 tasks)",
                new Quiz(new EquationTaskGenerator(0, 100, true, true, true, true), 5));
        generators.put("Equation mode (10 tasks)",
                new Quiz(new EquationTaskGenerator(0, 100, true, true, true, true), 10));
        generators.put("Equation mode (15 tasks)",
                new Quiz(new EquationTaskGenerator(0, 100, true, true, true, true), 15));
        generators.put("Text mode (3 tasks)",
                new Quiz(new TextTaskGenerator(TextTaskGenerator.DifficultyLevel.HARD), 3));
        generators.put("Text mode (2 tasks)",
                new Quiz(new TextTaskGenerator(TextTaskGenerator.DifficultyLevel.NIGHTMARE), 2));
        return generators;
    }
    public static void main(String[] args) throws FileNotFoundException {
        System.out.print("Want to create your mode or chose an existing one? (YES/NO)");
        Scanner in = new Scanner(System.in);
        if (Objects.equals(in.nextLine(), "YES")) {
            System.out.println("Choose task mode:");
            System.out.println("Expression / Equation / Text");
            String custom_mode = in.nextLine();
            System.out.println("Enter task count:");
            String custom_task_count = in.nextLine();
            if (Objects.equals(custom_mode, "Text")) {
                System.out.println("Enter difficulty level:");
                System.out.println("Hard / Nightmare");
                String custom_level = in.nextLine();
                if (Objects.equals(custom_level, "Hard")) {
                    Quiz quiz = new Quiz(new TextTaskGenerator(TextTaskGenerator.DifficultyLevel.HARD),
                            Integer.parseInt(custom_task_count));
                    quiz.StartQuiz();
                } else {
                    new Quiz(new TextTaskGenerator(TextTaskGenerator.DifficultyLevel.NIGHTMARE),
                            Integer.parseInt(custom_task_count)).StartQuiz();
                    return;
                }
            } else {
                System.out.println("Enable sum tasks?");
                System.out.println("Yes / No");
                String blank = in.nextLine();
                boolean sum = false;
                if (Objects.equals(blank, "Yes")) {
                    sum = true;
                }
                System.out.println("Enable difference tasks?");
                System.out.println("Yes / No");
                blank = in.nextLine();
                boolean dif = false;
                if (Objects.equals(blank, "Yes")) {
                    dif = true;
                }
                System.out.println("Enable multiply tasks?");
                System.out.println("Yes / No");
                blank = in.nextLine();
                boolean mul = false;
                if (Objects.equals(blank, "Yes")) {
                    mul = true;
                }
                System.out.println("Enable sum tasks?");
                System.out.println("Yes / No");
                blank = in.nextLine();
                boolean div = false;
                if (Objects.equals(blank, "Yes")) {
                    div = true;
                }
                System.out.println("Enter max number");
                int max = Integer.parseInt(in.nextLine());
                System.out.println("Enter min number");
                int min = Integer.parseInt(in.nextLine());

                if (Objects.equals(custom_mode, "Expression")) {
                    new Quiz(new ExpressionTaskGenerator(min, max, sum,dif, mul, div),
                            Integer.parseInt(custom_task_count)).StartQuiz();
                    return;
                }
                if (Objects.equals(custom_mode, "Equation")) {
                    new Quiz(new EquationTaskGenerator(min, max, sum,dif, mul, div),
                            Integer.parseInt(custom_task_count)).StartQuiz();
                    return;
                }
            }

        }
        System.out.println("Choose task mode (Expression / Equation / Text):");
        Map<String, Quiz> generators = getQuizMap();
        for (Map.Entry<String, Quiz> it : generators.entrySet())
        {
            System.out.println(it.getKey());
        }
        String mode = in.nextLine();
        System.out.println("Choose tasks count:");
        String count = in.nextLine();
        generators.get(mode + " mode " + "(" + count + " tasks)").StartQuiz();
        in.close();
    }
}