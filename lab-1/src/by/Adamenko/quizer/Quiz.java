package by.Adamenko.quizer;

import by.Adamenko.quizer.exceptions.QuizNotFinishedException;
import by.Adamenko.quizer.task_generators.*;
import by.Adamenko.quizer.task_generators.math_task_generators.*;
import by.Adamenko.quizer.tasks.Task;
import by.Adamenko.quizer.tasks.TextTask;
import by.Adamenko.quizer.tasks.math_tasks.Operator;

import java.util.ArrayList;
import java.util.EnumSet;
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
        Map<String, Quiz> tests = new HashMap<>();
        EnumSet<Operator> operators_1 = EnumSet.of(Operator.Minus, Operator.Plus, Operator.Divide, Operator.Multiple);
        EnumSet<Operator> operators_2 = EnumSet.of(Operator.Plus);

        tests.put("EqTaskOnlyPlus", new Quiz(new EquationMathTaskGenerator(0, 10, operators_1), 5));
        tests.put("EqTaskAll", new Quiz(new EquationMathTaskGenerator(-10, 10, operators_1), 5));
        tests.put("ExprTaskOnlyPlus", new Quiz(new ExpressionMathTaskGenerator(0, 10, operators_2), 5));
        tests.put("ExprTaskAll", new Quiz(new ExpressionMathTaskGenerator(-10, 10, operators_1), 5));
        tests.put("Group1", new Quiz(new GroupTaskGenerator(new ExpressionMathTaskGenerator(-10, 10, operators_1),
                new ExpressionMathTaskGenerator(0, 10, operators_2)), 5));

        ArrayList<TaskGenerator> list = new ArrayList<>();
        list.add(new EquationMathTaskGenerator(0, 10, operators_1));
        list.add(new EquationMathTaskGenerator(-10, 10, operators_2));

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
        try{
            if (!isFinished()) {
                throw new QuizNotFinishedException("Test isn't finished");
            }
        } catch (QuizNotFinishedException e) {
            e.fillInStackTrace();
        }
        if (amountOfTasks == 0) {
            return 10.0;
        }
        return (double) correctAnswers / (double) amountOfTasks * 10.0;
    }
}
