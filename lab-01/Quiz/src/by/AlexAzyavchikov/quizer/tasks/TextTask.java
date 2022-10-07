package by.AlexAzyavchikov.quizer.tasks;

import by.AlexAzyavchikov.quizer.Result;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask extends AbstractTask {
    protected String answer;

    public TextTask(String text,
                    String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public Result validate(String answer) {
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }
}
