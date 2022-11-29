package by.Savenok.quizer;

import by.Savenok.quizer.task_generators.TaskGenerator;
import by.Savenok.quizer.tasks.Task;

import java.util.Scanner;

public class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        generator_ = generator;
        task_count = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        current_task_ = generator_.generate();
        current_task_number++;
        return current_task_;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        return current_task_.validate(answer);
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return current_task_number >= task_count;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correct_answers_count_;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrong_answers_count_;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrect_input_count_;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() {
        if (getWrongAnswerNumber() == 0) {
            return getCorrectAnswerNumber();
        }
        return (double) getCorrectAnswerNumber() / getWrongAnswerNumber(); // // // // // //
    }

    public void StartQuiz() {
        Scanner in = new Scanner(System.in);
        while (!isFinished()) {
            current_task_ = generator_.generate();
            System.out.print(nextTask().getText());
            System.out.print('\n');
            String users_answer = in.nextLine();
            Result result = provideAnswer(users_answer);
            System.out.println(result.toString()); //
            if (result == Result.OK) {
                correct_answers_count_++;
                continue;
            }
            if (result == Result.WRONG) {
                wrong_answers_count_++;
                System.out.print(current_task_.getAnswer());
                System.out.print('\n');
            }
            if (result == Result.INCORRECT_INPUT) {
                incorrect_input_count_++;
            }
        }
        System.out.print(getMark());
        in.close();
    }
    int current_task_number = 0;
    int task_count;
    int correct_answers_count_ = 0;
    int wrong_answers_count_ = 0;
    int incorrect_input_count_ = 0;
    TaskGenerator generator_;
    Task current_task_;
}
