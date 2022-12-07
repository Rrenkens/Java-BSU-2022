package by.dudkoandrei.quizer.tasks;

import by.dudkoandrei.quizer.Result;

/**
 * Interface, который описывает одно задание.
 */
public interface Task {

  /**
   * Interface, который описывает один генератор заданий.
   */
  interface Generator {

    /**
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания
     * иммутабельный.
     *
     * @return задание
     * @see Task
     */
    Task generate();
  }

  /**
   * Возвращает текст задания.
   *
   * @return текст задания
   */
  String getText();

  /**
   * Проверяет ответ на задание и возвращает результат.
   *
   * @param answer ответ на задание
   * @return результат ответа
   * @see Result
   */
  Result validate(String answer);
}
