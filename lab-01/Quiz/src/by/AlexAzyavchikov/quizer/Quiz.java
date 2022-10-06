package by.AlexAzyavchikov.quizer;

import by.AlexAzyavchikov.quizer.task_generators.TaskGenerator;
import by.AlexAzyavchikov.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(TaskGenerator generator, int taskCount) {
        // ...
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() {
        // ...
        return null;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        // ...
        return null;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        // ...
        return false;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        // ...
        return 0;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        // ...
        return 0;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        // ...
        return 0;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    public double getMark() {
        // ...
        return 0;
    }
}
