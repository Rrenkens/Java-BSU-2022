package by.toharrius.quizer;

import by.toharrius.quizer.Task;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private TaskGenerator taskGenerator;
    private int taskCount;
    private Task currentTask = null;
    private Result lastResult = null;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        this.taskGenerator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    Task nextTask() {
        if (lastResult == Result.INCORRECT_INPUT) {
            currentTask = taskGenerator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        lastResult = currentTask.validate(answer);
        switch (lastResult) {
            case OK: {
                ++correctAnswerNumber;
            }
            case WRONG: {
                ++wrongAnswerNumber;
            }
            case INCORRECT_INPUT: {
                ++incorrectInputNumber;
            }
        }
        return lastResult;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return getCorrectAnswerNumber() +
                getWrongAnswerNumber() == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    int getTotalAnswerNumber() {
        return getCorrectAnswerNumber()
                + getIncorrectInputNumber()
                + getWrongAnswerNumber();
    }

    /**
     * @return Оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        return (double)getCorrectAnswerNumber() / getTotalAnswerNumber();
    }
}
