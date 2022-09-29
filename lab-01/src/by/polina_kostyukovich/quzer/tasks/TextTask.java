package by.polina_kostyukovich.quzer.tasks;

import by.polina_kostyukovich.quzer.Result;

public class TextTask implements Task {
    @Override
    public String getText() {
        return null;
    }

    @Override
    public Result validate(String answer) {
        return null;
    }

    public static class Generator implements Task.Generator {
        public Task generate() {
            return null;
        }
    }
}
