package by.CCivakova.quizer;

import by.CCivakova.quizer.tasks.Task;

import java.security.InvalidParameterException;

public class Quiz {
    private int cntOK = 0;
    private int cntWrong = 0;
    private int cntIncorrect = 0;

    private final Task.Generator generator;
    private final int taskCount;

    private boolean isLastIncorrect;
    private Task currentTask;

    public Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (!isLastIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        isLastIncorrect = false;
        switch (result) {
            case OK: {
                cntOK++;
                break;
            }
            case WRONG: {
                cntWrong++;
                break;
            }
            case INCORRECT_INPUT: {
                cntIncorrect++;
                isLastIncorrect = true;
                break;
            }
        }
        return result;
    }

    public boolean isFinished() {
        return (cntOK + cntWrong == taskCount);
    }

    public int getCorrectAnswerNumber() {
        return cntOK;
    }

    public int getWrongAnswerNumber() {
        return cntWrong;
    }

    public int getIncorrectInputNumber() {
        return cntIncorrect;
    }

    public double getMark() {
        if (isFinished() || taskCount == 0) {
            return ((double)(cntOK) / taskCount);
        } else {
            throw new InvalidParameterException();
        }
    }
}
