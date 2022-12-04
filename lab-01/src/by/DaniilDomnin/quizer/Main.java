package by.DaniilDomnin.quizer;

import exceptions.TaskGeneratorException;
import task_generators.GroupTaskGenerator;
import task_generators.PoolTaskGenerator;
import task_generators.math_task_generators.EquationTaskGenerator;
import task_generators.math_task_generators.ExpressionTaskGenerator;
import task_generators.math_task_generators.MathTaskGenerator;
import tasks.TextTask;

import java.util.*;

public class Main {
    static Map<String, Quiz> getQuizMap() throws TaskGeneratorException {
        EnumSet<MathTaskGenerator.Operation> operations;
        operations = EnumSet.of(MathTaskGenerator.Operation.MULTIPLICATION);
        operations.add(MathTaskGenerator.Operation.SUM);
        operations.add(MathTaskGenerator.Operation.DIFFERENCE);
        operations.add(MathTaskGenerator.Operation.DIVISION);

        EquationTaskGenerator equation_generator = new EquationTaskGenerator(1, 10, operations);
        ExpressionTaskGenerator expressionTaskGenerator = new ExpressionTaskGenerator(3, 100, operations);
        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(equation_generator, expressionTaskGenerator);
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> text_tasks = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            tasks.add(groupTaskGenerator.generate());
        }
        PoolTaskGenerator poolTaskGenerator = new PoolTaskGenerator(true, tasks);
        text_tasks.add(new TextTask("2^2", "4"));
        text_tasks.add(new TextTask("x + 4 + 3 = 10","3"));
        text_tasks.add(new TextTask("x + 4 + 3 = 10","3"));
        PoolTaskGenerator text_tasks_generator = new PoolTaskGenerator(false, text_tasks);

        Quiz q1 = new Quiz(equation_generator, 5);
        Quiz q2 = new Quiz(expressionTaskGenerator, 5);
        Quiz q3 = new Quiz(groupTaskGenerator, 5);
        Quiz q4 = new Quiz(poolTaskGenerator, 5);
        Quiz q5 = new Quiz(text_tasks_generator, 2);

        Map<String, Quiz> quiz = new HashMap<>();
        quiz.put("Equations", q1);
        quiz.put("Expressions", q2);
        quiz.put("Groups", q3);
        quiz.put("Pool", q4);
        quiz.put("Text", q5);
        return quiz;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Map<String, Quiz> map = getQuizMap();
        System.out.println("Quiz name");
        Quiz q = map.get(in.next());
        while (!q.isFinished()) {
            Task task = q.nextTask();
            System.out.println(task.getText());
            String answer = in.next();
            while (true) {
                Result res = q.provideAnswer(answer);
                if (res == Result.WRONG) {
                    System.out.println("wrong");
                    break;
                } else if (res == Result.OK) {
                    System.out.println("Correct");
                    break;
                }
                System.out.println("Incorrect, try again");
                answer = in.next();
            }
        }
        System.out.println("Mark: "+q.getMark() * 10);
    }
}