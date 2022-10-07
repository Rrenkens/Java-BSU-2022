package by.DaniilDomnin.quizer;

import exceptions.QuizFinishedException;
import exceptions.QuizNotFinishedException;

import javax.lang.model.type.NullType;
import java.util.Scanner;

class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        taskGenerator = generator;
        is_next_task = true;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() throws QuizFinishedException {
        if (!isFinished()) {
            if (is_next_task) {
                current_task = taskGenerator.generate();
                taskCount++;
            }
            return current_task;
        }
        throw new QuizFinishedException("Quiz finished");
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result res = current_task.validate(answer);
        is_next_task = true;
        switch (res) {
            case OK -> correct_count++;
            case WRONG -> incorrect_count++;
            case INCORRECT_INPUT -> is_next_task = false;
        }
        return current_task.validate(answer);
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return incorrect_count + incorrect_count == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correct_count;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return incorrect_count;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (isFinished()) {
            return (double) correct_count / taskCount;
        }
        throw new QuizNotFinishedException("Quiz not finished");
    }

     private TaskGenerator taskGenerator;
    private int taskCount;
    private int correct_count = 0;
    private int incorrect_count = 0;

    private Task current_task;
    private boolean is_next_task;
}
