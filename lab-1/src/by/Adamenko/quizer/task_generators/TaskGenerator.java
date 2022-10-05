package by.Adamenko.quizer.task_generators;

import by.Adamenko.quizer.tasks.Task;

/**
 * Interface, который описывает один генератор заданий
 */
public interface TaskGenerator {
    /*
     * Возвращает задание. При этом новый объект может не создаваться, если класс задания иммутабельный
     *
     * @return задание
     * @see    by.Adamenko.quizer.tasks.Task
     */
    Task generate();
}