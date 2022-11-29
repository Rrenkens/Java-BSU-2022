package by.parfen01.quiser;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private final Task.Generator generator;
    private int taskCount;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputCount = 0;
    Task currentTask;
    Task nextTask;
    boolean isLastInputIncorrect = false;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
        this.nextTask = null;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() {
        if (isLastInputIncorrect) {
            return currentTask;
        }
        if (nextTask == null) {
            nextTask = generator.generate();
        }
        Task result = nextTask;
        currentTask = nextTask;
        if (taskCount > 1) {
            nextTask = generator.generate();
        }
        return result;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case INCORRECT_INPUT -> {
                isLastInputIncorrect = true;
                ++incorrectInputCount;
            }
            case OK -> {
                ++correctAnswerNumber;
                --taskCount;
                isLastInputIncorrect = false;
            }
            case WRONG -> {
                ++wrongAnswerNumber;
                --taskCount;
                isLastInputIncorrect = false;
            }
        }

        return result;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return taskCount == 0;
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return  this.correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return this.wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return this.incorrectInputCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() {
        return (double)correctAnswerNumber / (correctAnswerNumber + wrongAnswerNumber) * 10;
    }
}