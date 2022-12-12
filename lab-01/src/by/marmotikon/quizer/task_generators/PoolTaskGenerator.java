package by.marmotikon.quizer.task_generators;

import by.marmotikon.quizer.exceptions.EmptyTaskPoolException;
import by.marmotikon.quizer.tasks.Task;
import by.marmotikon.quizer.tasks.Task.TaskGenerator;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private List<Task> tasks;
    private final Random random = new Random();

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, List.of(tasks));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = new ArrayList<>(tasks.stream().toList());
        if (!allowDuplicate) {
            this.tasks = new ArrayList<>();
            for(var task1 : tasks) {
                boolean isDuplicate = false;
                for(var task2 : this.tasks) {
                    if (task1.equals(task2)) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    this.tasks.add(task1);
                }
            }
        }
        Collections.shuffle(this.tasks);
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new EmptyTaskPoolException("trying to generate more tasks than given to PoolTaskGenerator with banned duplicates");
        }
        int indexOfTask = random.nextInt(tasks.size());
        Task task = tasks.get(indexOfTask).copy();
        if (!allowDuplicate) {
            tasks.remove(indexOfTask);
        }
        return task;
    }
}