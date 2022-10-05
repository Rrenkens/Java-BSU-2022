package by.Adamenko.quizer.task_generators;

import by.Adamenko.quizer.tasks.Task;

import java.util.*;

public class PoolTaskGenerator implements TaskGenerator {

    private final boolean duplicate;
    private ArrayList<Task> allTasks = new ArrayList<>();
    private HashSet<Task> set = new HashSet<>();
    private final Random rnd = new Random();

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Task... tasks
    ) {
        duplicate = allowDuplicate;
        allTasks.addAll(List.of(tasks));
        if (duplicate) {
            Collections.shuffle(allTasks);
        }
    }

    public PoolTaskGenerator(
            boolean allowDuplicate,
            Collection<Task> tasks
    ) {
       duplicate = allowDuplicate;
       allTasks.addAll(tasks);
       if (duplicate) {
           Collections.shuffle(allTasks);
       }
    }

    public Task generate() {
        if (duplicate) {
            int pos = rnd.nextInt(0, allTasks.size());
            return allTasks.get(pos);
        }
        if (allTasks.isEmpty()) {
            // TODO throw
        }
        Task x = allTasks.get(allTasks.size() - 1);
        allTasks.remove(x);
        return x;
    }
}
