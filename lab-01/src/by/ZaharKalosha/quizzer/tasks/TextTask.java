package by.ZaharKalosha.quizzer.tasks;

import by.ZaharKalosha.quizzer.Result;
import by.ZaharKalosha.quizzer.Task;
import by.ZaharKalosha.quizzer.task_generators.PoolTaskGenerator;


/**
 * Задание с заранее заготовленным текстом.
 * Можно использовать {@link PoolTaskGenerator}, чтобы задавать задания такого типа.
 */
public class TextTask implements Task {
    private final String text;
    private final String answer;
    private Boolean strictCase;

    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    public TextTask(String text, String answer, boolean strictCase) {
        this.text = text;
        this.answer = answer;
        this.strictCase = strictCase;
    }

    @Override
    public String getAnswer() {
        return answer;
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

        if (strictCase) {
            if (answer == this.answer) {
                return Result.OK;
            }
        } else {
            if (answer.equalsIgnoreCase(this.answer)) {
                return Result.OK;
            }
        }

        return Result.WRONG;
    }
}