package by.ManFormTheMoon.quizer.task_generators;

import by.ManFormTheMoon.quizer.tasks.Task;

import java.util.*;

public class PoolTaskGenerator implements Task.Generator {
    private final boolean allowDuplicate;
    private final List<Task> tasks;
    private int currentTask = 0;

    public PoolTaskGenerator(
            boolean allowDuplicate_,
            Task... tasks_
    ) {
        allowDuplicate = allowDuplicate_;
        if (allowDuplicate_) {
            tasks = Arrays.asList(tasks_);
        } else {
            tasks = new ArrayList<>();
            tasks.addAll(new HashSet(Arrays.asList(tasks_)));
            Collections.shuffle(tasks);
        }
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate_ разрешить повторения
     * @param tasks_          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(
            boolean allowDuplicate_,
            Collection<Task> tasks_
    ) {
        allowDuplicate = allowDuplicate_;
        if (allowDuplicate_) {
            tasks = new ArrayList<>(tasks_);
        } else {
            tasks = new ArrayList<>();
            tasks.addAll(new HashSet(new ArrayList<>(tasks_)));
            Collections.shuffle(tasks);
        }
    }

    @Override
    public Task generate() {
        if (allowDuplicate) {
            int index = (int) (Math.random() * tasks.size());
            return tasks.get(index);
        } else {
            // TODO : add exception
            currentTask++;
            if (currentTask < tasks.size()) {
                return tasks.get(currentTask);
            }
            return null;
        }
    }
}
