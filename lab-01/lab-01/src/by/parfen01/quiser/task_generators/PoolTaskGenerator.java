package by.parfen01.quiser.task_generators;

import by.parfen01.quiser.Task;
import by.parfen01.quiser.TaskGenerator;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private LinkedHashSet<String> usedTextsOfTasks;
    private boolean isAllowedDuplicate;
    private int numberOfUsedTasks = 0;

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
        for (Task task : tasks) {
            if (task != null) {
                this.tasks.add(task);
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
            return tasks.get(numberOfTest);
        }
        if (numberOfUsedTasks == tasks.size()) {
            throw new EmptyStackException();
        }

        while(true) {
            int numberOfTest = (int) (Math.random() * tasks.size());
            if (!usedTextsOfTasks.contains(tasks.get(numberOfTest).getText())) {
                ++numberOfUsedTasks;
                usedTextsOfTasks.add(tasks.get(numberOfTest).getText());
                return tasks.get(numberOfTest);
            }
        }
    }
}
