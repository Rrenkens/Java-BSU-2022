package by.kalllegar.quizer.task_generators;

import by.kalllegar.quizer.Task;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Генератор заданий инициализирующийся перечнем доступных заданий.
 * Одноразовый. Если хотите использовать один и тот же генератор в разных
 * квизах передавайте его копию
 */
public class PoolTaskGenerator implements TaskGenerator {
    private final ArrayList<Task> tasks = new ArrayList<Task>();
    private boolean allowDuplicate;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, Arrays.stream(tasks).collect(Collectors.toList()));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        LinkedHashSet<String> unique = new LinkedHashSet<>();
        for (Task task : tasks) {
            if (task != null && !unique.contains(task.getText())) {
                unique.add(task.getText());
                this.tasks.add(task);
            }
        }
        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.allowDuplicate = allowDuplicate;
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (this.allowDuplicate) {
            int numberOfTest = (int) (Math.random() * tasks.size());
            return tasks.stream().skip(numberOfTest).limit(1).findFirst().get();
        }
        if (tasks.isEmpty()) {
            throw new EmptyStackException();
        }
        int numberOfTest = (int) (Math.random() * tasks.size());
        Task result = tasks.stream().skip(numberOfTest).limit(1).findFirst().get();
        tasks.remove(result);
        return result;
    }

    @Override
    public PoolTaskGenerator clone() {
        return new PoolTaskGenerator(allowDuplicate, tasks);
    }
}
