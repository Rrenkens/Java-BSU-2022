package by.kalllegar.quizer.task_generators;

import by.kalllegar.quizer.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GroupTaskGenerator implements TaskGenerator {
    private final ArrayList<TaskGenerator> generators = new ArrayList<>();

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        if (generators == null) {
            throw new NullPointerException();
        }
        for (var generator : generators) {
            if (generator != null) {
                this.generators.add(generator);
            }
        }
        if (this.generators.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<TaskGenerator> generators) {
        if (generators == null) {
            throw new NullPointerException();
        }
        this.generators.addAll(generators);
        if (this.generators.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        Collections.shuffle(generators);
        for (var generator : generators) {
            try {
                return generator.generate();
            } catch (Exception ignore) {
            }
        }
        throw new RuntimeException();
    }
}