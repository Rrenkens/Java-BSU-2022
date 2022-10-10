package by.polina_kostyukovich.quizer.task_generators;

import by.polina_kostyukovich.quizer.exceptions.TooFewTasksException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.*;

import static by.polina_kostyukovich.quizer.task_generators.RandomIndexesGenerator.getRandomIndexes;

public class PoolTaskGenerator implements Task.Generator {
    private final Task[] tasks;
    private final boolean allowDuplicate;
    private final int[] randomIndexes;
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
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = tasks;
            randomIndexes = null;
        } else {
            this.tasks = getTasksWithoutDuplicate(tasks);
            randomIndexes = RandomIndexesGenerator.getRandomIndexes(this.tasks.length);
        }
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
        this.allowDuplicate = allowDuplicate;
        Task[] tasksArray = tasks.toArray(new Task[0]);
        if (allowDuplicate) {
            this.tasks = tasksArray;
            randomIndexes = null;
        } else {
            this.tasks = getTasksWithoutDuplicate(tasksArray);
            randomIndexes = RandomIndexesGenerator.getRandomIndexes(this.tasks.length);
        }
    }

    private static Task[] getTasksWithoutDuplicate(Task[] tasks) {
        if (tasks.length == 0) {
            return new Task[0];
        }

        Arrays.sort(tasks, Comparator.comparing(Task::getText));
        Task current_task = tasks[0];
        int numberOfDifferentTasks = 1;
        for (Task task : tasks) {
            if (!current_task.getText().equals(task.getText())) {
                ++numberOfDifferentTasks;
                current_task = task;
            }
        }

        Task[] differentTasks = new Task[numberOfDifferentTasks];
        int current_index = 0;
        current_task = tasks[0];
        for (Task task : tasks) {
            if (!current_task.getText().equals(task.getText())) {
                ++current_index;
                current_task = task;
                differentTasks[current_index] = task;
            }
        }
        return differentTasks;
    }

    /**
     * @return случайная задача из списка
     */
    @Override
    public Task generate() {
        if (tasks.length == 0) {
            throw new TooFewTasksException("The list of tasks is empty");
        }
        if (allowDuplicate) {
            int randomIndex = (int) (Math.random() * tasks.length);
            return tasks[randomIndex];
        } else {
            if (currentIndex >= tasks.length) {
                throw new TooFewTasksException("Various tasks have ended");
            }
            ++currentIndex;
            return tasks[randomIndexes[currentIndex - 1]];
        }
    }
}
