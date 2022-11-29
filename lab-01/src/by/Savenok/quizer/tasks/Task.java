package by.Savenok.quizer.tasks;

import by.Savenok.quizer.Result;

public interface Task {
    /*
     @return текст задания
     */
    String getText();

    String getAnswer();
    /*
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);
}
