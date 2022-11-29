package by.CCivakova.quizer.task_generators;

import by.CCivakova.quizer.tasks.Task;
import by.CCivakova.quizer.utilities.ShuffleManager;

import java.util.Collection;
import java.util.List;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    public GroupTaskGenerator(Task.Generator... generators_) {
        generators = generators_;
    }

    public GroupTaskGenerator(Collection<Task.Generator> generators_) {
        generators = generators_.toArray(new Task.Generator[0]);
    }

    @Override
    public Task generate() {
        List<Integer> indexes = ShuffleManager.getRandomPermutation(generators.length);
        for (int i : indexes) {
            try {
                return (generators[i]).generate();
            } catch (Exception exception) {}
        }
        throw new RuntimeException();
    }
}
