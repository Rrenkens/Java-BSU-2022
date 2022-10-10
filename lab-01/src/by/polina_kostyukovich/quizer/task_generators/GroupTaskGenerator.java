package by.polina_kostyukovich.quizer.task_generators;

import by.polina_kostyukovich.quizer.exceptions.BadGeneratorsException;
import by.polina_kostyukovich.quizer.exceptions.TooFewGeneratorsException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Array of generators is null");
        }
        this.generators = generators;
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
        this.generators = generators.toArray(new Task.Generator[0]);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
        if (generators.length == 0) {
            throw new TooFewGeneratorsException("The list of generators is empty");
        }
        int[] randomIndexes = RandomIndexesGenerator.getRandomIndexes(generators.length);
        for (int randomIndex : randomIndexes) {
            try {
                return generators[randomIndex].generate();
            } catch (RuntimeException ignored) {}
        }
        throw new BadGeneratorsException("All generators threw exceptions");
    }
}
