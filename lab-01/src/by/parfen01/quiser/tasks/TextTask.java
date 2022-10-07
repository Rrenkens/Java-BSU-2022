package by.parfen01.quiser.tasks;

import by.parfen01.quiser.Result;
import by.parfen01.quiser.Task;
import by.parfen01.quiser.task_generators.PoolTaskGenerator;

import java.util.Objects;


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
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new NullPointerException();
        }
        if (answer.equals(this.answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
