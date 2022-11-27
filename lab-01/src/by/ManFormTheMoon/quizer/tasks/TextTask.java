package by.ManFormTheMoon.quizer.tasks;

import by.ManFormTheMoon.quizer.Result;
import by.ManFormTheMoon.quizer.task_generators.PoolTaskGenerator;

public class TextTask implements Task {
    private final String text;
    private final String answer;

    public TextTask(String text_, String answer_) {
        text = text_;
        answer = answer_;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer_) {
        return answer.equals(answer_) ? Result.OK : Result.WRONG;
    }

    public static class Generator implements Task.Generator {
        private final PoolTaskGenerator poolTaskGenerator;

        public Generator(TextTask[] tasks) {
            if (tasks.length == 0) {
                throw new IllegalArgumentException();
            }
            poolTaskGenerator = new  PoolTaskGenerator(true, tasks);
        }

        public Task generate() {
            return poolTaskGenerator.generate();
        }
    }
}

