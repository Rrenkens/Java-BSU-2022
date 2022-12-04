package by.DaniilDomnin.quizer;

import exceptions.QuizFinishedException;
import exceptions.QuizNotFinishedException;
import exceptions.TaskGeneratorException;

class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        taskGenerator = generator;
        isNextTask = true;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() throws QuizFinishedException, TaskGeneratorException {
        if (!isFinished()) {
            if (isNextTask) {
                currentTask = taskGenerator.generate();
            }
            return currentTask;
        }
        throw new QuizFinishedException("Quiz finished");
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result res = currentTask.validate(answer);
        isNextTask = true;
        switch (res) {
            case OK -> correctCount++;
            case WRONG -> incorrectCount++;
            case INCORRECT_INPUT -> isNextTask = false;
        }
        return res;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return correctCount + incorrectCount == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return incorrectCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (isFinished()) {
            return (double) correctCount / taskCount;
        }
        throw new QuizNotFinishedException("Quiz not finished");
    }

     private final TaskGenerator taskGenerator;
    private int taskCount;
    private int correctCount = 0;
    private int incorrectCount = 0;

    private Task currentTask;
    private boolean isNextTask;
}
