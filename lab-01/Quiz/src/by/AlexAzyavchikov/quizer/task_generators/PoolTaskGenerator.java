package by.AlexAzyavchikov.quizer.task_generators;

import by.AlexAzyavchikov.quizer.exceptions.NoTasksException;
import by.AlexAzyavchikov.quizer.tasks.Task;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {
    boolean allowDuplicates;
    List<Task> tasks;

    public PoolTaskGenerator(boolean allowDuplicate,
                             Task... tasks) {
        this(allowDuplicate, List.of(tasks));
    }

    public PoolTaskGenerator(boolean allowDuplicate,
                             Collection<Task> tasks) {
        this.allowDuplicates = allowDuplicate;
        if (!allowDuplicate) {
            Set<Task> unique = new HashSet<>(tasks);
            this.tasks = new LinkedList<>(unique);
        } else {
            this.tasks = new ArrayList<>(tasks);
        }
    }

    public Task generate() {
        if (tasks.isEmpty()) {
            throw new NoTasksException("No tasks left in PoolTaskGenerator");
        } else {
            if (allowDuplicates) {
                return tasks.get((int) (Math.random() * tasks.size()));
            }
            Task task = ((LinkedList<Task>) tasks).getLast();
            ((LinkedList<Task>) tasks).removeLast();
            return task;
        }
    }
}
