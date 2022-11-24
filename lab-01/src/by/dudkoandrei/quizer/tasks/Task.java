package by.dudkoandrei.quizer.tasks;

import by.dudkoandrei.quizer.Result;

/**
 * Interface, который описывает одно задание.
 */
public interface Task {

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