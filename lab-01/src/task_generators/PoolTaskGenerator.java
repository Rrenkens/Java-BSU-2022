package task_generators;

import by.DaniilDomnin.quizer.Task;
import by.DaniilDomnin.quizer.TaskGenerator;
import exceptions.NotEnoughTasksException;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        this.tasks = new ArrayList<>();
        is_allowed_duplicate = allowDuplicate;
        if (tasks.length == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(List.of(tasks));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        this.tasks = new ArrayList<>();
        is_allowed_duplicate = allowDuplicate;
        if (tasks.size() == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        Collections.shuffle(this.tasks);
       if (!is_allowed_duplicate) {
           return tasks.get(0);
       } else {
           if (tasks.size() == 0) {
               throw new NotEnoughTasksException("Pool tasks is ended");
           }
           Task return_task = tasks.get(tasks.size() - 1);
           tasks.remove(tasks.size() - 1);
           return return_task;
       }
    }

    private ArrayList<Task> tasks;
    private boolean is_allowed_duplicate;
}
