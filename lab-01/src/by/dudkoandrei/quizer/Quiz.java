package by.dudkoandrei.quizer;

import by.dudkoandrei.quizer.tasks.Task;

/**
 * Class, который описывает один тест.
 */
class Quiz {

  private final Task.Generator taskGenerator;

  private Task currentTask = null;
  private final int taskCount;
  private boolean answeredOnCurrentTask = false;

  private int correctAnswerNumber = 0;
  private int wrongAnswerNumber = 0;
  private int incorrectInputNumber = 0;

  /**
   * @param generator генератор заданий
   * @param taskCount количество заданий в тесте
   */
  Quiz(Task.Generator generator, int taskCount) {
    if (taskCount <= 0) {
      // exception
    }
    if (generator == null) {
      // exception
    }
    this.taskGenerator = generator;
    this.taskCount = taskCount;
  }

  /**
   * @return задание, повторный вызов вернет слелующее
   * @see Task
   */
  Task nextTask() {
    if (isFinished()) {
      // exception
    }

    if (answeredOnCurrentTask || currentTask == null) {
      currentTask = taskGenerator.generate();
      answeredOnCurrentTask = false;
    }

    return currentTask;
  }

  /**
   * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик
   * неправильных ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же
   * самый объект {@link Task}.
   */
  Result provideAnswer(String answer) {
    if (isFinished()) {
      // exception
    }

    Result result = currentTask.validate(answer);

    switch (result) {
      case OK -> {
        correctAnswerNumber++;
        answeredOnCurrentTask = true;
      }
      case WRONG -> {
        wrongAnswerNumber++;
        answeredOnCurrentTask = true;
      }
      case INCORRECT_INPUT -> incorrectInputNumber++;
    }

    return result;
  }

  /**
   * @return завершен ли тест
   */
  boolean isFinished() {
    return correctAnswerNumber + wrongAnswerNumber == taskCount;
  }

  /**
   * @return количество правильных ответов
   */
  int getCorrectAnswerNumber() {
    return correctAnswerNumber;
  }

  /**
   * @return количество неправильных ответов
   */
  int getWrongAnswerNumber() {
    return wrongAnswerNumber;
  }

  /**
   * @return количество раз, когда был предоставлен неправильный ввод
   */
  int getIncorrectInputNumber() {
    return incorrectInputNumber;
  }

  /**
   * @return оценка, которая является отношением количества правильных ответов к количеству всех
   * вопросов. Оценка выставляется только в конце!
   */
  double getMark() {
    if (!isFinished()) {
      // exception
    }

    return ((double) correctAnswerNumber) / taskCount;
  }
}
