package by.parfen01.quiser;

/**
 * Class, который описывает один тест
 */
class Quiz {
    TaskGenerator generator;
    int taskCount;
    int correctAnswerNumber = 0;
    int wrongAnswerNumber = 0;
    int incorrectInputCount = 0;
    Task currentTask;
    boolean isLastInputIncorrect = false;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
        currentTask = generator.generate();
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (!isLastInputIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case INCORRECT_INPUT -> {
                isLastInputIncorrect = true;
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
    boolean isFinished() {
        return taskCount == 0;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return  this.correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return this.wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return this.incorrectInputCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        return (double)correctAnswerNumber / (correctAnswerNumber + wrongAnswerNumber);
    }
}