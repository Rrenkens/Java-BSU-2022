package by.AlexAzyavchikov.quizer.tasks;

import by.AlexAzyavchikov.quizer.Result;
import by.AlexAzyavchikov.quizer.Task;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    String text;
    String answer;

    TextTask(String text,
             String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }
}
