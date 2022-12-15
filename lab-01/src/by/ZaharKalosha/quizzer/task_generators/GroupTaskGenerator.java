package by.ZaharKalosha.quizzer.task_generators;

import by.ZaharKalosha.quizzer.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GroupTaskGenerator implements Task.Generator {
    private final ArrayList<Task.Generator> generators = new ArrayList<>();
    private int offset = 0;

    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.Generator... generators) {
        this(List.of(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<Task.Generator> generators) {
        if (generators == null) {
            throw new IllegalArgumentException("Collection = null");
        }

        if (generators.isEmpty()) {
            throw new IllegalArgumentException("Collection - empty");
        }

        for (Task.Generator generator : generators) {
            if (generator != null) {
                this.generators.add(generator);
            }
        }
        Collections.shuffle(this.generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() {
        for (int i = 1; i <= generators.size(); i++) {
            try {
                Task task = generators.get((i + offset) % generators.size()).generate();
                offset = (i + offset) % generators.size();
                return task;
            } catch (Exception ignored) {

            }
        }

        throw new RuntimeException("All generators have problems");
    }
}