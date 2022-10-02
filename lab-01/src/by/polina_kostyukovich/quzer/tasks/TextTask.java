package by.polina_kostyukovich.quzer.tasks;

import by.polina_kostyukovich.quzer.Result;

public class TextTask implements Task {
    private final String text;
    private final String answer;

    public TextTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
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
        return answer.equals(this.answer) ? Result.OK : Result.WRONG;
    }

    public static class Generator implements Task.Generator {
        public Task generate() {
            return null;
        }
    }
}
