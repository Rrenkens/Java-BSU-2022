package by.CCivakova.quizer.task_generators;

import by.CCivakova.quizer.tasks.Task;
import by.CCivakova.quizer.utilities.ShuffleManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoolTaskGenerator  implements Task.Generator {
    private final boolean allowDuplicate;
    private final Task[] tasks;
    private int taskIndex = 0;
    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = tasks;
        } else {
            List<Integer> new_indexes = ShuffleManager.getRandomPermutation(tasks.length);
            this.tasks = new Task[tasks.length];
            for (int i = 0; i < tasks.length; i++) {
                this.tasks[i] = tasks[new_indexes.get(i)];
            }
        }
    }

    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks = tasks.toArray(new Task[0]);
        } else {
            List<Integer> new_indexes = ShuffleManager.getRandomPermutation(tasks.size());
            this.tasks = new Task[tasks.size()];
            List<Task> temp = new ArrayList<>(tasks);
            for (int i = 0; i < tasks.size(); i++) {
                this.tasks[i] = temp.get(new_indexes.get(i));
            }
        }
    }
    @Override
    public Task generate() {
        if (allowDuplicate) {
            return tasks[(int)(Math.random() * tasks.length)];
        } else {
            if (taskIndex + 1 == tasks.length) {
                throw new OutOfMemoryError();
            }
            taskIndex++;
            return tasks[taskIndex];
        }
    }

}
