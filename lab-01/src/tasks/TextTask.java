package tasks;

import by.DaniilDomnin.quizer.Result;
import by.DaniilDomnin.quizer.Task;
import task_generators.PoolTaskGenerator;

import java.util.Objects;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */


    public TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        try {
            if (Objects.equals(this.answer, answer)) {
                return Result.OK;
            }
            return Result.WRONG;
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    private String text;
    private String answer;
}
