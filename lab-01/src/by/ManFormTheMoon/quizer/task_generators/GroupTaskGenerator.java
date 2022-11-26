package by.ManFormTheMoon.quizer.task_generators;

import by.ManFormTheMoon.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < generators.length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int index : list) {
            try {
                return generators[index].generate();
            } catch (Exception e) {}
        }
        // TODO : add exception
        return null;
    }
}
