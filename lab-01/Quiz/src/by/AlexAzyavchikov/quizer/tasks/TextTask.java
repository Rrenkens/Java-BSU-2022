package by.AlexAzyavchikov.quizer.tasks;

import by.AlexAzyavchikov.quizer.Result;

public class TextTask implements Task {
    protected String text;
    protected String answer;

    public TextTask(String text,
                    String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TextTask)) {
            return false;
        }
        return this.text.equals(((Task) o).getText());
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
