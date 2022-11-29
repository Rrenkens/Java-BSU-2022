package by.dudkoandrei.quizer.tasks;

import by.dudkoandrei.quizer.Result;

/**
 * Задание с заранее заготовленным текстом. Можно использовать {@link PoolTaskGenerator}, чтобы
 * задавать задания такого типа.
 */
public class TextTask implements Task {

  private final String text;
  private final String answer;

  /**
   * @param text   текст задания
   * @param answer ответ на задание
   */
  public TextTask(String text, String answer) {
    if (text == null || answer == null) {
      throw new IllegalArgumentException("Arguments shouldn't be null");
    }
    if (text.isEmpty() || answer.isEmpty()) {
      throw new IllegalArgumentException("Arguments shouldn't be empty");
    }

    this.text = text;
    this.answer = answer;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public Result validate(String answer) {
    if (answer == null) {
      throw new IllegalArgumentException("answer is null");
    }

    if (answer.isEmpty()) {
      return Result.INCORRECT_INPUT;
    }

    if (answer.equalsIgnoreCase(this.answer)) {
      return Result.OK;
    } else {
      return Result.WRONG;
    }
  }
}