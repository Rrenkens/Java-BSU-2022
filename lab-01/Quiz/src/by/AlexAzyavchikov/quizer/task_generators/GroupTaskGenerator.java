package by.AlexAzyavchikov.quizer.task_generators;

import by.AlexAzyavchikov.quizer.exceptions.NoTasksException;
import by.AlexAzyavchikov.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GroupTaskGenerator implements TaskGenerator {
    protected ArrayList<TaskGenerator> generators;

    public GroupTaskGenerator(TaskGenerator... generators) {
        this(List.of(generators));
    }

    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<>(generators);
    }

    public Task generate() {
        if (generators.isEmpty()) {
            throw new NoTasksException("Unable to generate task in GroupTaskGenerator: no generators were provided");
        }

        Collections.shuffle(generators);
        for (TaskGenerator generator : generators) {
            try {
                return generator.generate();
            } catch (NoTasksException ignored) {
            }
        }
        throw new NoTasksException("Unable to generate task in GroupTaskGenerator: all generators throw exceptions");
    }
}
