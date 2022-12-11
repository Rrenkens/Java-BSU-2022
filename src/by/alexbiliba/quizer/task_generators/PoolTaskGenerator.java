package by.alexbiliba.quizer.task_generators;

import by.alexbiliba.quizer.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PoolTaskGenerator implements Task.Generator {
    boolean allowDuplicate;
    int tasksAllow;
    Collection<Task> tasks = null;
    ArrayList<Boolean> task_used = new ArrayList<>();

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
        tasksAllow = tasks.length;
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<Task>();
        this.tasks.addAll(Arrays.asList(tasks));
        for (int i = 0; i < tasks.length; i++) {
            task_used.add(false);
        }
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
        tasksAllow = tasks.size();
        this.allowDuplicate = allowDuplicate;
        this.tasks = tasks;
        for (int i = 0; i < tasks.size(); i++) {
            task_used.add(false);
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        Task newTask = null;
        int taskIndex = 0;
        if (allowDuplicate) {
            taskIndex = ThreadLocalRandom.current().nextInt(0, tasks.size());
        } else {
            if (tasksAllow == 0) {
                return null;
            }
            while (task_used.get(taskIndex)) {
                taskIndex = ThreadLocalRandom.current().nextInt(0, tasks.size());
            }
            tasksAllow--;
            task_used.set(taskIndex, true);
        }

        int currentTaskIndex = 0;
        for (Task task : tasks) {
            if (taskIndex == currentTaskIndex) {
                newTask = task;
                break;
            }
            currentTaskIndex++;
        }
        return newTask;
    }
}
