package by.marmotikon.quizer;

import by.marmotikon.quizer.exceptions.QuizAlreadyFinishedException;
import by.marmotikon.quizer.exceptions.QuizNotFinishedException;
import by.marmotikon.quizer.tasks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static by.marmotikon.quizer.Result.*;

/**
 * Class, который описывает один тест
 */
class Quiz {
//    private final ArrayList<Task> tasks;
    private Task currentTask;
    private final Map<Result, Integer> Answers;
//    private int index = -1;
    private boolean wasIncorrectInput = false;

    private final Task.TaskGenerator generator;
    private final int taskCount;


    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.TaskGenerator generator, int taskCount) throws IllegalArgumentException{
        if (taskCount <= 0) {
            throw new IllegalArgumentException("can't create quiz with less then one task");
        }
        Answers = new HashMap<>(3);
        Answers.put(OK, 0);
        Answers.put(WRONG, 0);
        Answers.put(INCORRECT_INPUT, 0);
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    void nextTask() throws QuizAlreadyFinishedException{
        if (wasIncorrectInput) {
            return;
//            return currentTask;
        }
        if (isFinished()) {
            throw new QuizAlreadyFinishedException("trying to get new task when quiz is finished");
        }
        try {
            currentTask = generator.generate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) throws IllegalCallerException {
        Result result = currentTask.validate(answer);
        Answers.put(result, Answers.get(result) + 1);
        wasIncorrectInput = result == INCORRECT_INPUT;
        return result;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return Answers.get(OK) + Answers.get(WRONG) == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return Answers.get(OK);
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return Answers.get(WRONG);
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return Answers.get(INCORRECT_INPUT);
    }

    final String getAnswer() {
        return currentTask.getAnswerString();
    }
    final String getText() {
        return currentTask.getText();
    }

    /**
     * @return Оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException{
        if (!isFinished()) {
            throw new QuizNotFinishedException("trying to get mark before finishing quiz");
        }
        return (float) (Answers.get(OK) * 10) / taskCount;
    }
}
