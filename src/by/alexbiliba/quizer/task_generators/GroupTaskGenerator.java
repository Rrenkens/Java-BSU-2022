package by.alexbiliba.quizer.task_generators;

import by.alexbiliba.quizer.*;

import java.util.*;

import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements Task.Generator {
    Collection<Task.Generator> generators = null;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this.generators = new ArrayList<Task.Generator>();
        this.generators.addAll(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<Task.Generator> generators) {
        this.generators = generators;
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        int generatorIndex = ThreadLocalRandom.current().nextInt(0, generators.size());
        int currentGeneratorIndex = 0;
        for (Task.Generator generator : generators) {
            if (generatorIndex == currentGeneratorIndex) {
                return generator.generate();
            }
            currentGeneratorIndex++;
        }
        return null;
    }
}