package by.dudkoandrei.quizer.task_generators;

import by.dudkoandrei.quizer.tasks.Task;

/**
 * Interface, который описывает один генератор заданий
 */
public interface TaskGenerator {

  /**
   * Возвращает задание. При этом новый объект может не создаваться, если класс задания
   * иммутабельный.
   *
   * @return задание
   * @see Task
   */
  Task generate();
}
