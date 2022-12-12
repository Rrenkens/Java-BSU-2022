package by.marmotikon.quizer.tasks;

import by.marmotikon.quizer.Result;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /**
     * Interface, который описывает один генератор заданий
     */
    interface TaskGenerator {
        /**
         * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
         *
         * @return задание
         * @see    Task
         */
        Task generate() throws Exception;
    }

    /**
     *@return текст задания
     */
    String getText();

    /**
     *@return ответ на задание
     */
    String getAnswerString();

    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);

    boolean equals(Task other);

    Task copy();
}
