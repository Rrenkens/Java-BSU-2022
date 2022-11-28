package by.polina_kostyukovich.quizer.task_generators;

import by.polina_kostyukovich.quizer.exceptions.TooFewTasksException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.*;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements Task.Generator {
    private final List<Task> tasks;
    private final boolean allowDuplicate;
    private int currentIndex = 0;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Array of tasks is null");
        }
        if (tasks.length == 0) {
            throw new IllegalArgumentException("Array of tasks is empty");
        }
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = Arrays.asList(tasks);
        } else {
            this.tasks = getTasksWithoutDuplicate(tasks);
        }
        Collections.shuffle(this.tasks);
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Collection of tasks is null");
        }
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Collection of tasks is empty");
        }
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = new ArrayList<>(tasks);
        } else {
            this.tasks = getTasksWithoutDuplicate(tasks.toArray(new Task[0]));
        }
        Collections.shuffle(this.tasks);
    }

    private static List<Task> getTasksWithoutDuplicate(Task[] tasks) {
        return Arrays.stream(tasks)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (allowDuplicate) {
            if (currentIndex == tasks.size()) {
                Collections.shuffle(tasks);
            }
            currentIndex = currentIndex % tasks.size() + 1;
        } else {
            if (currentIndex >= tasks.size()) {
                throw new TooFewTasksException("Various tasks have ended");
            }
            ++currentIndex;
        }
        return tasks.get(currentIndex - 1);
    }
}
