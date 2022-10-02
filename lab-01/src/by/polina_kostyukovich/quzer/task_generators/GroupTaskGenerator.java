package by.polina_kostyukovich.quzer.task_generators;

import by.polina_kostyukovich.quzer.tasks.Task;

import java.util.Collection;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = generators;
    }

    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = generators.toArray(new Task.Generator[0]);
    }

    @Override
    public Task generate() {
        int index = (int) (Math.random() * generators.length);
        return generators[index].generate();
    }
}
