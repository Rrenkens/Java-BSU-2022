package by.AlexAzyavchikov.quizer.task_generators;

import by.AlexAzyavchikov.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class PoolTaskGenerator implements TaskGenerator {
    boolean allowDuplicates;
    ArrayList<Task> tasks = new ArrayList<>();

    PoolTaskGenerator(boolean allowDuplicate,
                      Task... tasks) {
        this.allowDuplicates = allowDuplicate;
        this.tasks.addAll(List.of(tasks));
    }

    PoolTaskGenerator(boolean allowDuplicate,
                      Collection<Task> tasks) {
        this.allowDuplicates = allowDuplicate;
        this.tasks.addAll(tasks);
    }

    public Task generate() {
        assert !tasks.isEmpty();
        if (allowDuplicates) {
            return tasks.get((int) (Math.random() * tasks.size()));
        }
        int last = tasks.size() - 1;
        Task task = tasks.get(last);
        tasks.remove(last);
        return task;
    }
}
