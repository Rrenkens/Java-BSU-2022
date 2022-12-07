package by.dudkoandrei.quizer.task_generators;

import by.dudkoandrei.quizer.exceptions.NoMoreTasksException;
import by.dudkoandrei.quizer.tasks.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Task.Generator, который отдает задания из заранее заготовленного набора.
 */
public class PoolTaskGenerator implements Task.Generator {

  private final List<Task> tasks;
  private final boolean allowDuplicate;
  private int currentPosition = 0;
  private static final Random rnd = new Random();

  /**
   * Конструктор с переменным числом аргументов.
   *
   * @param allowDuplicate разрешить повторения
   * @param tasks          задания, которые в конструктор передаются через запятую
   */
  public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
    this(allowDuplicate, Arrays.asList(tasks));
  }

  /**
   * Конструктор, который принимает коллекцию заданий.
   *
   * @param allowDuplicate разрешить повторения
   * @param tasks          задания, которые передаются в конструктор в Collection (например,
   *                       {@link LinkedList})
   */
  public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
    if (tasks == null) {
      throw new IllegalArgumentException("tasks is null");
    }
    if (tasks.isEmpty()) {
      throw new IllegalArgumentException("tasks is empty");
    }
    if (tasks.contains(null)) {
      throw new IllegalArgumentException("tasks contains null");
    }

    this.allowDuplicate = allowDuplicate;

    if (!allowDuplicate) {
      this.tasks = tasks.stream().distinct().collect(Collectors.toList());
    } else {
      this.tasks = new ArrayList<>(tasks);
    }

    Collections.shuffle(this.tasks);
  }

  /**
   * @return случайная задача из списка
   */
  public Task generate() {
    if (allowDuplicate) {
      return tasks.get(rnd.nextInt(tasks.size()));
    }

    if (currentPosition == tasks.size()) {
      throw new NoMoreTasksException();
    }

    return tasks.get(currentPosition++);
  }
}
