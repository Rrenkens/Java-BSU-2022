package task_generators;

import by.DaniilDomnin.quizer.Task;
import by.DaniilDomnin.quizer.TaskGenerator;
import exceptions.QuizNotFinishedException;
import exceptions.TaskGeneratorException;

import java.util.*;

public class GroupTaskGenerator implements TaskGenerator {
    /**
     * Конструктор с переменным числом аргументов
     *
     * @param generators генераторы, которые в конструктор передаются через запятую
     */
    public GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = new ArrayList<>();
        if (generators.length == 0) {
            throw new IllegalArgumentException("generators size must be more 0");
        }
        this.generators.addAll(Arrays.asList(generators));
    }

    /**
     * Конструктор, который принимает коллекцию генераторов
     *
     * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
     */
    public GroupTaskGenerator(Collection<TaskGenerator> generators) {
        this.generators = new ArrayList<>();
        if (generators.isEmpty()) {
            throw new IllegalArgumentException("generators size must be more 0");
        }
        this.generators.addAll(generators);
    }

    /**
     * @return результат метода generate() случайного генератора из списка.
     *         Если этот генератор выбросил исключение в методе generate(), выбирается другой.
     *         Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
     */
    public Task generate() throws TaskGeneratorException {
        Collections.shuffle(generators);
        int index = 0;
        while (index < generators.size()) {
            try {
                return generators.get(index).generate();
            } catch (Exception e) {
                index++;
            }
        }
        throw new TaskGeneratorException("Generator Exception");
    }

    private final ArrayList<TaskGenerator> generators;
}