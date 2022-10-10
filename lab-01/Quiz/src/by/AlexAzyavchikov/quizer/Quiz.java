package by.AlexAzyavchikov.quizer;

import by.AlexAzyavchikov.quizer.exceptions.*;
import by.AlexAzyavchikov.quizer.task_generators.TaskGenerator;
import by.AlexAzyavchikov.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    private int taskCount;
    private int correctlyFinishedTasks;
    private int wrongFinishedTasks;
    private int incorrectInputtedTasks;
    private TaskGenerator taskGenerator;

    private Task currentTask;

    public Quiz(TaskGenerator generator, int taskCount) {
        this.taskCount = taskCount;
        this.taskGenerator = generator;
        correctlyFinishedTasks = 0;
        wrongFinishedTasks = 0;
        incorrectInputtedTasks = 0;
    }

    public Task nextTask() {
        if (isFinished()) {
            throw new QuizFinishedException("Quiz has already finished. No tasks left");
        }
        if (currentTask == null) {
            currentTask = taskGenerator.generate();
        }
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        if (currentTask == null) {
            throw new EarlyAnswerException("No task provided. There is nothing to check with answer.");
        }
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK -> {
                correctlyFinishedTasks++;
                currentTask = null;
            }
            case WRONG -> {
                wrongFinishedTasks++;
                currentTask = null;
//                TODO: if need repeats delete previous line
            }
            case INCORRECT_INPUT -> {
                incorrectInputtedTasks++;
            }
        }
        return result;
    }

    public boolean isFinished() {
        return correctlyFinishedTasks == taskCount;
    }

    int getCorrectAnswerNumber() {
        return correctlyFinishedTasks;
    }

    int getWrongAnswerNumber() {
        return wrongFinishedTasks;
    }

    int getIncorrectInputNumber() {
        return incorrectInputtedTasks;
    }

    public double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Quiz is not finished yet. Unable yo get mark.");
        }
        return (double) (Math.max(taskCount - wrongFinishedTasks, 0)) / taskCount;
    }
}
