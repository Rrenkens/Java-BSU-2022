package by.alexbiliba.quizer.tasks;

import by.alexbiliba.quizer.*;
import by.alexbiliba.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    String text;
    String answer;

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

    public String getAnswer() {
        return answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer.equals(this.answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
