package by.dudkoandrei.quizer;

import by.dudkoandrei.quizer.constants.EquationTaskGeneratorConstants;
import by.dudkoandrei.quizer.constants.ExpressionTaskGeneratorConstants;
import by.dudkoandrei.quizer.constants.TextGeneratorConstats;
import by.dudkoandrei.quizer.task_generators.GroupTaskGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  /**
   * @return тесты в {@link Map}, где ключ - название теста {@link String} значение - сам тест
   * {@link Quiz}
   */
  static Map<String, Quiz> getQuizMap() {
    Map<String, Quiz> quizMap = new HashMap<>();

    quizMap.put("Text Quiz", new Quiz(TextGeneratorConstats.generator, 3));
    quizMap.put("Integer equations", new Quiz(EquationTaskGeneratorConstants.integerGenerator, 10));
    quizMap.put("Equations", new Quiz(EquationTaskGeneratorConstants.generator, 10));
    quizMap.put("Integer expressions",
        new Quiz(ExpressionTaskGeneratorConstants.integerGenerator, 10));
    quizMap.put("Expressions", new Quiz(ExpressionTaskGeneratorConstants.generator, 10));
    quizMap.put("All tasks", new Quiz(
        new GroupTaskGenerator(TextGeneratorConstats.generator,
            EquationTaskGeneratorConstants.integerGenerator,
            EquationTaskGeneratorConstants.generator,
            ExpressionTaskGeneratorConstants.integerGenerator,
            ExpressionTaskGeneratorConstants.generator), 20));

    return quizMap;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    var quizMap = getQuizMap();

    System.out.println("Available quizzes:");

    for (var name : quizMap.keySet()) {
      System.out.println(name);
    }

    System.out.println("Select quiz:");

    String name = scanner.nextLine();
    while (!quizMap.containsKey(name)) {
      System.out.println("No such quiz. Try again");
      name = scanner.nextLine();
    }

    Quiz quiz = quizMap.get(name);

    System.out.println("Quiz: \"" + name + "\" started");
    while (!quiz.isFinished()) {
      System.out.println(quiz.nextTask().getText());

      String answer = scanner.nextLine();
      Result result = quiz.provideAnswer(answer);

      System.out.println(result + "\n");
    }

    System.out.println("Quiz ended");
    System.out.println("Your score: " + quiz.getMark() * 10);
    System.out.println("Correct answers: " + quiz.getCorrectAnswerNumber());
    System.out.println("Wrong answers: " + quiz.getWrongAnswerNumber());
    System.out.println("Incorrect input: " + quiz.getIncorrectInputNumber());
  }
}
