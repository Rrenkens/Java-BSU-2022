package by.alexbiliba.quizer;

import java.util.ArrayList;

/**
 * Class, который описывает один тест
 */
class Quiz {

    ArrayList<Task> tasks = new ArrayList<>();
    int current_task_index = -1;

    int AC_number = 0;
    int WA_number = 0;
    int II_number = 0;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        while (taskCount > 0) {
            Task new_task = generator.generate();
            tasks.add(new_task);
            taskCount--;
        }
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        current_task_index++;
        Task current_task = tasks.get(current_task_index);
        return current_task;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Task current_task = tasks.get(current_task_index);
        Result current_result = current_task.validate(answer);
        if (current_result == Result.OK) {
            AC_number++;
        } else if (current_result == Result.WRONG) {
            WA_number++;
        } else {
            II_number++;
        }
        return current_result;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return current_task_index >= tasks.size() - 1;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return AC_number;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return WA_number;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return II_number;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        return ((double) AC_number) / ((double) tasks.size());
    }
}