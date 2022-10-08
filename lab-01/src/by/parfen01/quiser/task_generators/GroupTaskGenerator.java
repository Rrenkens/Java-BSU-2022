package by.parfen01.quiser.task_generators;

import by.parfen01.quiser.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GroupTaskGenerator implements Task.Generator {
    private final ArrayList<Task.Generator> generators = new ArrayList<>();
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        if (generators == null) {
            throw new NullPointerException();
        }
        for (Task.Generator generator : generators) {
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
    GroupTaskGenerator(Collection<Task.Generator> generators) {
        if (generators == null) {
            throw new NullPointerException();
        }
        for (Task.Generator generator : generators) {
            if (generator != null) {
                this.generators.add(generator);
            }
        }
        if (this.generators.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        Collections.shuffle(generators);
        for (int i = 0; i < generators.size(); ++i) {
            try {
                return generators.get(i).generate();
            }
            catch (Exception e) {
                if (i == generators.size()) {
                    throw e;
                }
            }
        }

        throw new RuntimeException();
    }
}