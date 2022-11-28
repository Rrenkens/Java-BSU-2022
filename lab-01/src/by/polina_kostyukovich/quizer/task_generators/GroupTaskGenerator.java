package by.polina_kostyukovich.quizer.task_generators;

import by.polina_kostyukovich.quizer.exceptions.BadGeneratorException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.Generator {
    private final List<Task.Generator> generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Array of generators is null");
        }
        if (generators.length == 0) {
            throw new IllegalArgumentException("Array of generators is empty");
        }
        this.generators = Arrays.asList(generators);
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Collection of generators is null");
        }
        if (generators.isEmpty()) {
            throw new IllegalArgumentException("Collection of generators is empty");
        }
        this.generators = new ArrayList<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
        Collections.shuffle(generators);
        for (Task.Generator generator : generators) {
            try {
                return generator.generate();
            } catch (RuntimeException ignored) {}
        }
        throw new BadGeneratorException("All generators threw exceptions");
    }
}
