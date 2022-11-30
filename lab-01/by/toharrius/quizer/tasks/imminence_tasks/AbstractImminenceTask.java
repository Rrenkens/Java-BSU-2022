package by.toharrius.quizer.tasks.imminence_tasks;

import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;

public class AbstractImminenceTask implements Task {
    private final String text;
    private final Result result;

    protected AbstractImminenceTask(String text, Result result) {
        this.text = text;
        this.result = result;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return result;
    }
}
