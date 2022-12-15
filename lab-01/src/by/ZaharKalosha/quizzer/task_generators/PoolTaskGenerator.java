package by.ZaharKalosha.quizzer.task_generators;

import by.ZaharKalosha.quizzer.Task;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Генератор заданий инициализирующийся перечнем доступных заданий.
 * Одноразовый. Если хотите использовать один и тот же генератор в разных
 * квизах передавайте его копию
 */
public class PoolTaskGenerator implements Task.Generator {
    private List<Task> tasks = new ArrayList<>();
    private final boolean duplicationAllowed;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, List.of(tasks));
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        duplicationAllowed = allowDuplicate;
        if (tasks == null) {
            throw new NullPointerException("Collection of tasks - null");
        }

        for (Task task : tasks) {
            if (task != null) {
                this.tasks.add(task);
            }
        }
        if (!duplicationAllowed) {
            this.tasks = tasks.stream().distinct().collect(Collectors.toList());
        }

        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (tasks.isEmpty()) {
            throw new EmptyStackException();
        }

        int numberOfTest = (int) (Math.random() * tasks.size());

        return tasks.stream().skip(numberOfTest).findFirst().get();
    }

    @Override
    public PoolTaskGenerator clone() {
        return new PoolTaskGenerator(duplicationAllowed, tasks);
    }
}
