package by.toharrius.quizer;

import by.toharrius.quizer.task_generators.TaskGenerator;
import by.toharrius.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
public class Quiz implements Cloneable {
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private final TaskGenerator taskGenerator;
    private final int taskCount;
    private Task currentTask = null;
    private Result lastResult = null;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(TaskGenerator generator, int taskCount) {
        this.taskGenerator = generator;
        this.taskCount = taskCount;
    }

    /**
     * Copy constructor refreshing state
     * @param other object to copy
     */
    public Quiz(Quiz other) throws Exception {
        this(other.taskGenerator.getClass().getConstructor(TaskGenerator.class, CopyParameter.class)
                .newInstance(other.taskGenerator, CopyParameter.FLAG), other.taskCount);
    }
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    public Task nextTask() {
        if (lastResult != Result.INCORRECT_INPUT) {
            currentTask = taskGenerator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        lastResult = currentTask.validate(answer);
        switch (lastResult) {
            case OK -> ++correctAnswerNumber;
            case WRONG -> ++wrongAnswerNumber;
            case INCORRECT_INPUT -> ++incorrectInputNumber;
        }
        return lastResult;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return getCurrentTaskIndex() == taskCount;
    }

    public int getCurrentTaskIndex() {
        return getCorrectAnswerNumber() +
                getWrongAnswerNumber();
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    public int getTotalAnswerNumber() {
        return getCorrectAnswerNumber()
                + getIncorrectInputNumber()
                + getWrongAnswerNumber();
    }

    /**
     * @return Оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() {
        return (double)getCorrectAnswerNumber() / getTotalAnswerNumber();
    }
}
