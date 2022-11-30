package by.toharrius.quizer.task_generators;

import by.toharrius.quizer.CopyParameter;
import by.toharrius.quizer.Task;
import by.toharrius.quizer.TaskGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

class GroupTaskGenerator implements TaskGenerator {
    ArrayList<TaskGenerator> generators;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<>();
        Collections.addAll(this.generators, generators);
    }

    public GroupTaskGenerator(TaskGenerator other, CopyParameter f) {
        this(((GroupTaskGenerator)other).generators);
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Iterable<TaskGenerator> generators) {
        this.generators = new ArrayList<>();
        for (var gen : generators) {
            this.generators.add(gen);
        }
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        var r = ThreadLocalRandom.current();
        int index = r.nextInt(generators.size());
        return generators.get(index).generate();
    }
}
