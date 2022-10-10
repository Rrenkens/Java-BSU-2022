package by.polina_kostyukovich.quizer.tasks;

import by.polina_kostyukovich.quizer.Result;
import by.polina_kostyukovich.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    private final String text;
    private final String answer;

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(String text, String answer) {
        if (text == null) {
            throw new IllegalArgumentException("Text of the task is null");
        }
        if (answer == null) {
            throw new IllegalArgumentException("Answer to the task is null");
        }
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public Result validate(String answer) {
        return answer.equals(this.answer) ? Result.OK : Result.WRONG;
    }

    public static class Generator implements Task.Generator {
        public Task generate() {
            // todo
            return null;
        }
    }
}
