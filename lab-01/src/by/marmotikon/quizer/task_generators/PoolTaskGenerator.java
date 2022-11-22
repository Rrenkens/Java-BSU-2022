package by.marmotikon.quizer.task_generators;

import by.marmotikon.quizer.exceptions.EmptyTaskPoolException;
import by.marmotikon.quizer.tasks.Task;
import by.marmotikon.quizer.tasks.Task.TaskGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private final List<Task> tasks;
    private final Random random = new Random();
    private final List<Integer> freeTaskIndexes;


    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = List.of(tasks);
        freeTaskIndexes = Stream.iterate(0, n -> n + 1)
                .limit(this.tasks.size())
                .collect(Collectors.toList());
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = tasks.stream().toList();
        System.out.println(this.tasks.size() + " size");
        freeTaskIndexes = Stream.iterate(0, n -> n + 1)
                .limit(this.tasks.size())
                .collect(Collectors.toList());
    }

    /**
     * @return случайная задача из списка
     */
    public Task generate() {
        System.out.println("freeTaks size " + freeTaskIndexes.size());
        if (freeTaskIndexes.isEmpty()) {
            throw new EmptyTaskPoolException("trying to generate more tasks than given to PoolTaskGenerator with banned duplicates");
        }
        int indexOfTaskIndex = random.nextInt(freeTaskIndexes.size());
        int taskIndex = freeTaskIndexes.get(indexOfTaskIndex);
        if (!allowDuplicate) {
            freeTaskIndexes.remove(indexOfTaskIndex);
        }
        return tasks.get(taskIndex);
    }
}