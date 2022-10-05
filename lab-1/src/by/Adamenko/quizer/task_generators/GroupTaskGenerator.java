package by.Adamenko.quizer.task_generators;

import by.Adamenko.quizer.exceptions.NoGenerators;
import by.Adamenko.quizer.tasks.Task;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {
    private final ArrayList<TaskGenerator> allGenerators = new ArrayList<>();
    private final Random rnd = new Random();

    public GroupTaskGenerator(TaskGenerator... generators) {
        Collections.addAll(allGenerators, generators);
    }

    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        allGenerators.addAll(generators);
    }

    public Task generate() {
        try {
            if (allGenerators.isEmpty()) {
                throw new NoGenerators("GroupTask");
            }
        } catch (NoGenerators e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        int pos = rnd.nextInt(0, allGenerators.size());
        return allGenerators.get(pos).generate();
    }
}
