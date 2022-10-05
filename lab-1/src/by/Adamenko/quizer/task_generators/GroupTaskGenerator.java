package by.Adamenko.quizer.task_generators;

import by.Adamenko.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class GroupTaskGenerator implements TaskGenerator {
    private ArrayList<TaskGenerator> allGenerators = new ArrayList<TaskGenerator>();
    private final Random rnd = new Random();

    public GroupTaskGenerator(TaskGenerator... generators) {
        for (int i = 0; i < generators.length; ++i) {
            Task x = generators[i].generate();
            // TODO catch
            allGenerators.add(generators[i]);
        }
    }

    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        allGenerators.addAll(generators);
        for (TaskGenerator generator : generators) {
            Task x = generator.generate();
            // TODO catch
            allGenerators.add(generator);
        }
    }

    // TODO throw
    public Task generate() {
        if (allGenerators.isEmpty()) {
            // TODO throw
        }
        int pos = rnd.nextInt(0, allGenerators.size());
        return allGenerators.get(pos).generate();
    }
}
