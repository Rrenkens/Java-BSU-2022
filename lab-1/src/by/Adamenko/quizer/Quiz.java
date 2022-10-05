package by.Adamenko.quizer;

import by.Adamenko.quizer.task_generators.*;
import by.Adamenko.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.Adamenko.quizer.task_generators.math_task_generators.ExpressionMathTaskGenerator;
import by.Adamenko.quizer.tasks.Task;
import by.Adamenko.quizer.tasks.TextTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Quiz {
    private final TaskGenerator taskGenerator;
    private final int amountOfTasks;
    private int wrongAnswers = 0;
    private int correctAnswers = 0;
    private int wrongFormat = 0;
    private Task currentTask;
    private Result lastResult;

    Quiz(TaskGenerator generator, int taskCount) {
        taskGenerator = generator;
        amountOfTasks = taskCount;
        lastResult = Result.OK;
    }

    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> tests = new HashMap<String, Quiz>();
        tests.put("EqTaskOnlyPlus", new Quiz(new EquationMathTaskGenerator(0, 10, true, false, false, false), 5));
        tests.put("EqTaskAll", new Quiz(new EquationMathTaskGenerator(-10, 10, true, true, true, true), 5));
        tests.put("ExprTaskOnlyPlus", new Quiz(new ExpressionMathTaskGenerator(0, 10, true, false, false, false), 5));
        tests.put("ExprTaskAll", new Quiz(new ExpressionMathTaskGenerator(-10, 10, true, true, true, true), 5));
        tests.put("Group1", new Quiz(new GroupTaskGenerator(new ExpressionMathTaskGenerator(-10, 10, true, true, true, true),
                new ExpressionMathTaskGenerator(0, 10, true, false, false, false)), 5));

        ArrayList<TaskGenerator> list = new ArrayList<>();
        list.add(new EquationMathTaskGenerator(0, 10, true, false, false, false));
        list.add(new EquationMathTaskGenerator(-10, 10, true, false, false, true));

        tests.put("Group2", new Quiz(new GroupTaskGenerator(list), 10));

        tests.put("Pol1", new Quiz(new PoolTaskGenerator(true, new TextTask("2 + 2 = ?", "4"),
                new TextTask("5 + 5 = ?", "10")), 5));

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new TextTask("x + 10 = 10", "0"));
        tasks.add(new TextTask("x * 50 = 100", "2"));

        tests.put("Pol2", new Quiz(new PoolTaskGenerator(true, tasks), 5));
        tests.put("Pol3", new Quiz(new PoolTaskGenerator(false, tasks), 2));
        return tests;
    }
    Task nextTask() {
        if (lastResult != Result.INCORRECT_INPUT) {
            currentTask = taskGenerator.generate();
        }
        return currentTask;
    }

    Result provideAnswer(String answer) {
        lastResult = currentTask.validate(answer);
        switch (lastResult) {
            case WRONG -> wrongAnswers++;
            case OK -> correctAnswers++;
            case INCORRECT_INPUT -> wrongFormat++;
        }
        return lastResult;
    }

    boolean isFinished() {
        return correctAnswers + wrongAnswers == amountOfTasks;
    }

    int getCorrectAnswerNumber() {
        return correctAnswers;
    }

    int getWrongAnswerNumber() {
        return wrongAnswers;
    }

    int getIncorrectInputNumber() {
        return wrongFormat;
    }

    double getMark() {
        return (double) correctAnswers / (double) amountOfTasks * 10.0;
    }
}
