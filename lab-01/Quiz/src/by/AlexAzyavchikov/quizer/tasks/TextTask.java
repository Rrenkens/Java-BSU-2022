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
    TextTask(
            String text,
            String answer
    ) {
        // ...
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public Result validate(String answer) {
        return null;
    }

    // ...
}
