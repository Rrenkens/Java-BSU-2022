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
        isAllowedDuplicate = allowDuplicate;
        if (tasks.length == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(List.of(tasks));
        if (!allowDuplicate) {
            DeleteDuplicates();
        }
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
        isAllowedDuplicate = allowDuplicate;
        if (tasks.size() == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(tasks);
        if (!allowDuplicate) {
            DeleteDuplicates();
        }
    }

    private void DeleteDuplicates () {
        HashSet<String> texts = new HashSet<>();
        ArrayList<Task> new_tasks = new ArrayList<>();
        for (var task : tasks) {
            if (!texts.contains(task.getText())) {
                new_tasks.add(task);
                texts.add(task.getText());
            }
        }
        tasks.clear();
        tasks.addAll(new_tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        Collections.shuffle(this.tasks);
       if (isAllowedDuplicate) {
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

    private final ArrayList<Task> tasks;
    private final boolean isAllowedDuplicate;
}
