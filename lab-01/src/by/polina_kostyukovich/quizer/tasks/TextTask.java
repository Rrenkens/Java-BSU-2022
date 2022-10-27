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
    private final boolean isAnswerInteger;

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
        this.text = text + "\n";
        this.answer = answer;
        isAnswerInteger = false;
    }

    public TextTask(String text, int answer) {
        if (text == null) {
            throw new IllegalArgumentException("Text of the task is null");
        }
        this.text = text + "\n";
        this.answer = String.valueOf(answer);
        isAnswerInteger = true;
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
        if (isAnswerInteger) {
            try {
                Integer.parseInt(answer);
                return answer.equals(this.answer) ? Result.OK : Result.WRONG;
            } catch (NumberFormatException exception) {
                return Result.INCORRECT_INPUT;
            }
        } else {
            try {
                Integer.parseInt(answer);
                return Result.INCORRECT_INPUT;
            } catch (NumberFormatException exception) {
                return answer.equals(this.answer) ? Result.OK : Result.WRONG;
            }
        }
    }

    public static class Generator implements Task.Generator {
        private final PoolTaskGenerator generator;

        public Generator(TextTask[] tasks) {
            generator = new PoolTaskGenerator(false, tasks);
        }

        public Task generate() {
            return generator.generate();
        }
    }
}
