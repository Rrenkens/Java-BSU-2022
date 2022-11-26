package by.ManFormTheMoon.quizer;

import by.ManFormTheMoon.quizer.tasks.Task;

import java.util.Map;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private final Task.Generator generator;

    private final int taskCount;

    private int countCorrect = 0;
    private int countIncorrect = 0;
    private int countIncorrectInput = 0;

    private Task currentTask;

    public Quiz(Task.Generator generator_, int taskCount_) {
        generator = generator_;
        taskCount = taskCount_;
        currentTask = generator.generate();
    }

    public Task nextTask() {
        return currentTask;
    }


    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK: {
                countCorrect++;
                currentTask = generator.generate();
                break;
            }
            case WRONG: {
                countIncorrect++;
                currentTask = generator.generate();
                break;
            }
            case INCORRECT_INPUT: {
                countIncorrectInput++;
                break;
            }
        }
        return result;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return (countCorrect + countIncorrect == taskCount);
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return countCorrect;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return countIncorrect;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return countIncorrectInput;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() {
        if (isFinished() || taskCount == 0) {
            return ((double)(countCorrect) / taskCount) * 10.0;
        } else {
            // TODO : add exception
            return 0;
        }
    }
}