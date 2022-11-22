package by.marmotikon.quizer.tasks;

import by.marmotikon.quizer.Result;
import by.marmotikon.quizer.task_generators.PoolTaskGenerator;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    String text;
    String answer;

    @Override
    public String getAnswer() {
        return answer;
    }

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    /**
     * @param other копируемое задание
     */
    public TextTask(TextTask other) {
        this.text = other.text;
        this.answer = other.answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return (answer.equalsIgnoreCase(this.answer) ? Result.OK : Result.WRONG);
    }
}
