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
        this.allowDuplicate = allowDuplicate;
        if (tasks.length == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(List.of(tasks));
        if (!allowDuplicate) {
            DeleteDuplicates();
        }
        Collections.shuffle(this.tasks);
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
        this.allowDuplicate = allowDuplicate;
        if (tasks.size() == 0) {
            throw new IllegalArgumentException("Tasks size must be more 0");
        }
        this.tasks.addAll(tasks);
        if (!allowDuplicate) {
            DeleteDuplicates();
        }
        Collections.shuffle(this.tasks);
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
        if (currentTaskIndex >= tasks.size()) {
            throw new NotEnoughTasksException("Pool tasks is ended");
        }
        Task ret = tasks.get(currentTaskIndex);
        currentTaskIndex++;
        return ret;
    }

    private final ArrayList<Task> tasks;

    private int currentTaskIndex = 0;
    private final boolean allowDuplicate;
}
