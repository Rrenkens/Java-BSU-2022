package by.CCivakova.quizer.tasks;

import by.CCivakova.quizer.Result;
import by.CCivakova.quizer.task_generators.PoolTaskGenerator;

import java.security.InvalidParameterException;

public class TextTask implements Task {
    private final String text;
    private final String answer;

    public TextTask(String text, String answer) {
        if (text == null || answer == null) {
            throw new InvalidParameterException();
        }
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
            throw new InvalidParameterException();
        }
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }

    public static class Generator implements Task.Generator {
        private final PoolTaskGenerator poolTaskGenerator;

        public Generator(TextTask[] tasks) {
            if (tasks.length == 0) {
                throw new InvalidParameterException();
            }
            poolTaskGenerator = new  PoolTaskGenerator(true, tasks);
        }

        public Task generate() {
            return poolTaskGenerator.generate();
        }
    }
}
