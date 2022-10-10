package by.polina_kostyukovich.quzer.task_generators;

import by.polina_kostyukovich.quzer.tasks.Task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

public class PoolTaskGenerator implements Task.Generator {
    private final Task[] tasks;
    private final boolean allowDuplicate;
    private final int[] randomIndexes;
    private int currentIndex = 0;

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = tasks;
            randomIndexes = null;
        } else {
            this.tasks = getTasksWithoutDuplicate(tasks);
            randomIndexes = createRandomIndexes(this.tasks.length);
        }
    }

    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        Task[] tasksArray = tasks.toArray(new Task[0]);
        if (allowDuplicate) {
            this.tasks = tasksArray;
            randomIndexes = null;
        } else {
            this.tasks = getTasksWithoutDuplicate(tasksArray);
            randomIndexes = createRandomIndexes(this.tasks.length);
        }
    }

    private static int[] createRandomIndexes(int size) {
        int[] indexes = new int[size];
        for (int i = 0; i < size; ++i) {
            indexes[i] = i;
        }

        Random rand = new Random();
        for (int i = 0; i < indexes.length; ++i) {
            int randomIndexToSwap = rand.nextInt(indexes.length);
            int temp = indexes[randomIndexToSwap];
            indexes[randomIndexToSwap] = indexes[i];
            indexes[i] = temp;
        }
        return indexes;
    }

    private static Task[] getTasksWithoutDuplicate(Task[] tasks) {
        if (tasks.length == 0) {
            return new Task[0];
        }

        Arrays.sort(tasks, Comparator.comparing(Task::getText));
        Task current_task = tasks[0];
        int numberOfUnique = 1;
        for (Task task : tasks) {
            if (!current_task.getText().equals(task.getText())) {
                ++numberOfUnique;
                current_task = task;
            }
        }

        Task[] uniqueTasks = new Task[numberOfUnique];
        int current_index = 0;
        current_task = tasks[0];
        for (Task task : tasks) {
            if (!current_task.getText().equals(task.getText())) {
                ++current_index;
                current_task = task;
                uniqueTasks[current_index] = task;
            }
        }
        return uniqueTasks;
    }

    @Override
    public Task generate() {
        if (allowDuplicate) {
            int randomIndex = (int) (Math.random() * tasks.length);
            return tasks[randomIndex];
        } else {
            if (currentIndex >= tasks.length) {
                // throw exception
            }
            ++currentIndex;
            return tasks[randomIndexes[currentIndex - 1]];
        }
    }
}
