package by.Savenok.quizer.tasks.maths_tasks;

import by.Savenok.quizer.Result;
import by.Savenok.quizer.tasks.Task;

import java.util.Objects;

public class ExpressionTask implements Task {
    public ExpressionTask(String statement, String answer) {
        statement_ = statement;
        answer_ = answer;
    }
    public String getText(){
        return statement_;
    }
    public Result validate(String answer){
        if (Objects.equals(answer, answer_)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
    public String getAnswer() {
        return answer_;
    }
    String statement_;
    String answer_;
}
