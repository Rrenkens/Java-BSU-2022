package by.marmotikon.quizer.tasks;

import by.marmotikon.quizer.Result;
import by.marmotikon.quizer.task_generators.PoolTaskGenerator;
import by.marmotikon.quizer.tasks.math_tasks.AbstractMathTask;

/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    String text;
    String answer;

    @Override
    public String getAnswerString() {
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

    @Override
    public boolean equals(Task other) {
        if (this == other) return true;
        if (!(other instanceof TextTask otherTextTask)) return false;
        return text.equals(otherTextTask.getText()) && answer.equals(otherTextTask.getAnswerString());
    }

    @Override
    public Task copy() {
        return new TextTask(text, answer);
    }
}
