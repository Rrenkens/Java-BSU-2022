package by.dudkoandrei.quizer.tasks;

import by.dudkoandrei.quizer.Result;

/**
 * Задание с заранее заготовленным текстом. Можно использовать {@link PoolTaskGenerator}, чтобы
 * задавать задания такого типа.
 */
class TextTask implements Task {

  private final String text;
  private final String answer;

  /**
   * @param text   текст задания
   * @param answer ответ на задание
   */
  TextTask(String text, String answer) {
    if (text == null || answer == null) {
      // exception
    }
    if (text.isEmpty() || answer.isEmpty()) {
      // exception
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
      // exception
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