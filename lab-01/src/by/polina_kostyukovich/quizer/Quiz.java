package by.polina_kostyukovich.quizer;

import by.polina_kostyukovich.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private final int taskCount;
    private final Task.Generator generator;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private Task currentTask;
    private boolean wasLastInputIncorrect = false;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(Task.Generator generator, int taskCount) {
        this.taskCount = taskCount;
        this.generator = generator;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() {
        if (!wasLastInputIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
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

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return wrongAnswerNumber + correctAnswerNumber == taskCount;
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

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() {
        if (!isFinished()) {
            // throw exception
        }
        return ((double) correctAnswerNumber) / taskCount;
    }
}
