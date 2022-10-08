package by.parfen01.quiser;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /*
     @return текст задания
     */

    String getText();

    /*
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);

    /**
     * Interface, который описывает один генератор заданий
     */
    interface Generator {
        /*
         * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
         *
         * @return задание
         * @see    Task
         */
        Task generate();
    }
}
