package by.Adamenko.quizer.tasks;

import by.Adamenko.quizer.Result;

public class TextTask implements Task {

    private final String Expression;
    private final String Answer;
    public TextTask(
            String text,
            String answer
    ) {
        Expression = text;
        Answer = answer;
    }

    @Override
    public String getText() {
        return Expression;
    }

    @Override
    public Result validate(String answer) {
        for (int i = 0; i < answer.length(); ++i) {
            if (!Character.isDigit(answer.charAt(i))) {
                if (i == 0 && answer.charAt(i) == '-') {
                    continue;
                }
                return Result.INCORRECT_INPUT;
            }
        }
        if (answer.equals(Answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
