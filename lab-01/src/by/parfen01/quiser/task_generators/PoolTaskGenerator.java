package by.parfen01.quiser.task_generators;

import by.parfen01.quiser.Task;
import java.util.*;

/**
 * Генератор заданий инициализирующийся перечнем доступных заданий.
 * Одноразовый. Если хотите использовать один и тот же генератор в разных
 * квизах передавайте его копию
 */
public class PoolTaskGenerator implements Task.Generator {
    private final LinkedHashSet<Task> tasks = new LinkedHashSet<>();
    private boolean isAllowedDuplicate;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые в конструктор передаются через запятую
     */
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        if (tasks == null) {
            throw new NullPointerException();
        }
        LinkedHashSet<String> uniqueTexts = new LinkedHashSet<>();
        for (Task task : tasks) {
            if (task != null && !uniqueTexts.contains(task.getText())) {
                this.tasks.add(task);
                uniqueTexts.add(task.getText());
            }
        }
        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
        isAllowedDuplicate = allowDuplicate;
    }

    /**
     * Конструктор, который принимает коллекцию заданий
     *
     * @param allowDuplicate разрешить повторения
     * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
     */
    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        if (tasks == null) {
            return;
        }
        LinkedHashSet<String> uniqueTexts = new LinkedHashSet<>();
        for (Task task : tasks) {
            if (task != null && !uniqueTexts.contains(task.getText())) {
                this.tasks.add(task);
                uniqueTexts.add(task.getText());
            }
        }
        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
        isAllowedDuplicate = allowDuplicate;
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (isAllowedDuplicate) {
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
        return new PoolTaskGenerator(isAllowedDuplicate, tasks);
    }
}
