package by.toharrius.quizer.task_generators;

import by.toharrius.quizer.CopyParameter;
import by.toharrius.quizer.Task;
import by.toharrius.quizer.TaskGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements TaskGenerator {
    private final ArrayList<Task> pool;
    private final boolean allowDuplicate;
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
        this(allowDuplicate, Arrays.stream(tasks).collect(Collectors.toList()));
    }

    public PoolTaskGenerator(TaskGenerator other, CopyParameter f) {
        this(((PoolTaskGenerator)other).allowDuplicate,
                ((PoolTaskGenerator)other).pool);
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
        this.pool = new ArrayList<>(tasks);
        this.allowDuplicate = allowDuplicate;
        if (!allowDuplicate) {
            Collections.shuffle(pool);
        }
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        if (allowDuplicate) {
            var r = ThreadLocalRandom.current();
            int index = r.nextInt(pool.size());
            return pool.get(index);
        } else {
            var task = pool.get(pool.size() - 1);
            pool.remove(pool.size() - 1);
            return task;
        }
    }
}
