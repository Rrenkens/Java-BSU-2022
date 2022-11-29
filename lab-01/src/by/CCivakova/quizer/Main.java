package by.CCivakova.quizer;

import by.CCivakova.quizer.task_generators.GroupTaskGenerator;
import by.CCivakova.quizer.task_generators.PoolTaskGenerator;
import by.CCivakova.quizer.tasks.TextTask;
import by.CCivakova.quizer.tasks.math_tasks.EquationTask;
import by.CCivakova.quizer.tasks.math_tasks.ExpressionTask;
import by.CCivakova.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class Main {
    public static final int MIN_MAX_VALUES = 100;
    public static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new TreeMap<>();

        ExpressionTask.Generator gen1 =
                new ExpressionTask.Generator(-MIN_MAX_VALUES, MIN_MAX_VALUES, EnumSet.allOf(MathTask.Operation.class));
        Quiz quiz1 = new Quiz(gen1, 10);
        quizMap.put("ex", quiz1);

        EquationTask.Generator gen2 =
                new EquationTask.Generator(-MIN_MAX_VALUES, MIN_MAX_VALUES, EnumSet.allOf(MathTask.Operation.class));
        Quiz quiz2 = new Quiz(gen2, 10);
        quizMap.put("eq", quiz2);


        PoolTaskGenerator gen3 = new PoolTaskGenerator(
                true,
                new TextTask("a + b", "ab"),
                new TextTask("reverse abc", "cba"),
                new TextTask("most popular symbol in abbabab", "b"),
                new TextTask("substr(1,2) s = 123456", "23"),
                new TextTask("Prosto hochy skazat, chto s vashei gruppi", "linal nikto ne sdast"),
                new TextTask("Molodoy chelovek", "vam 0")
        );
        Quiz quiz3 = new Quiz(gen3, 5);
        quizMap.put("txt", quiz3);

        GroupTaskGenerator gen4 = new GroupTaskGenerator(gen1, gen2, gen3);
        Quiz quiz4 = new Quiz(gen4, 10);
        quizMap.put("gr", quiz4);

        return quizMap;
    }

    public static void main(String[] args) {
        System.out.println("Pick test");
        Scanner scanner = new Scanner(System.in);
        Map<String, Quiz> quizMap = getQuizMap();
        String name;
        do {
            name = scanner.nextLine();
        } while (!quizMap.containsKey(name));

        Quiz currentQuiz = quizMap.get(name);
        while (!currentQuiz.isFinished()) {
            System.out.println(currentQuiz.nextTask().getText());
            Result result = currentQuiz.provideAnswer(scanner.nextLine());
            switch (result) {
                case OK -> {
                    System.out.println("OK");
                }
                case WRONG -> {
                    System.out.println("WRONG");
                }
                case INCORRECT_INPUT -> {
                    System.out.println("INCORRECT_INPUT");
                }
            }
        }
        System.out.println(currentQuiz.getMark());
    }
}