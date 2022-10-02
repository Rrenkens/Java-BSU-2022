package by.polina_kostyukovich.quzer.task_generators;

import by.polina_kostyukovich.quzer.tasks.Task;

import java.util.Collection;
import java.util.Random;

public class PoolTaskGenerator implements Task.Generator {
    private final Task[] tasks;
    private final boolean allowDuplicate;
    private final int[] randomIndexes;
    private int currentIndex = 0;

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = tasks;
        randomIndexes = createRandomIndexes(tasks.length);
    }

    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        this.tasks = tasks.toArray(new Task[0]);
        randomIndexes = createRandomIndexes(this.tasks.length);
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
