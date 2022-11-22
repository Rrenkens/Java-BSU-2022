package by.marmotikon.quizer.task_generators;

import by.marmotikon.quizer.exceptions.GroupTaskGeneratorException;
import by.marmotikon.quizer.tasks.Task;

import java.util.*;

public class GroupTaskGenerator implements Task.TaskGenerator {
    private final Vector<Task.TaskGenerator> generators;
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(Task.TaskGenerator... generators) {
        if (generators.length == 0) {
            throw new IllegalArgumentException("no generators are given to GroupTaskGenerator");
        }
        this.generators = new Vector<>(List.of(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    GroupTaskGenerator(Collection<Task.TaskGenerator> generators) {
        if (generators.size() == 0) {
            throw new IllegalArgumentException("no generators are given to GroupTaskGenerator");
        }
        this.generators = new Vector<>(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws GroupTaskGeneratorException {
        Random random = new Random();
        int index = random.nextInt(generators.size());
        int index_copy = index;
        while (true) {
            try {
                return generators.elementAt(index).generate();
            } catch (Exception e) {
                index %= generators.size();
                index++;
                if (index == index_copy) {
                    throw new GroupTaskGeneratorException("All generators throw exception");
                }
            }
        }
    }
}
