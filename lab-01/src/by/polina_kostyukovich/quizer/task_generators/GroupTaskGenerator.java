package by.polina_kostyukovich.quizer.task_generators;

import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = generators;
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = generators.toArray(new Task.Generator[0]);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    @Override
    public Task generate() {
        // check exceptions
        int index = (int) (Math.random() * generators.length);
        return generators[index].generate();
    }
}
