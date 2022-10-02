package by.polina_kostyukovich.quzer;

import by.polina_kostyukovich.quzer.tasks.Task;

import java.util.Map;

public class Quiz {
    private final int taskCount;
    private final Task.Generator generator;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private Task currentTask;
    private boolean wasLastInputIncorrect = false;

    public Quiz(Task.Generator generator, int taskCount) {
        this.taskCount = taskCount;
        this.generator = generator;
    }

    public Task nextTask() {
        if (!wasLastInputIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK -> {
                ++correctAnswerNumber;
                wasLastInputIncorrect = false;
            }
            case WRONG -> {
                ++wrongAnswerNumber;
                wasLastInputIncorrect = false;
            }
            case INCORRECT_INPUT -> {
                ++incorrectInputNumber;
                wasLastInputIncorrect = true;
            }
        }
        return result;
    }

    public boolean isFinished() {
        return wrongAnswerNumber + correctAnswerNumber == taskCount;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    public int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    public double getMark() {
        return ((double) correctAnswerNumber) / taskCount;
    }
}
