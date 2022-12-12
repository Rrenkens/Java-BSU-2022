package by.marmotikon.quizer;

import by.marmotikon.quizer.exceptions.QuizAlreadyFinishedException;
import by.marmotikon.quizer.tasks.Task;
import by.marmotikon.quizer.tasks.TextTask;
import by.marmotikon.quizer.tasks.math_tasks.ApplesTask.ApplesTaskGenerator;
import by.marmotikon.quizer.tasks.math_tasks.EquationTask.EquationTaskGenerator;
import by.marmotikon.quizer.tasks.math_tasks.ExpressionTask.ExpressionTaskGenerator;
import by.marmotikon.quizer.tasks.math_tasks.MathTask;
import by.marmotikon.quizer.task_generators.GroupTaskGenerator;
import by.marmotikon.quizer.task_generators.PoolTaskGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите название теста...");
        String enteredName = sc.nextLine();
        while (!quizMap.containsKey(enteredName)) {
            System.out.println("Неверное название теста. Попробуйте еще раз...");
            enteredName = sc.nextLine();
        }
        Quiz currentQuiz = quizMap.get(enteredName);
        while (!currentQuiz.isFinished()) {
            try {
                currentQuiz.nextTask();
            } catch (QuizAlreadyFinishedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(currentQuiz.getText());
            Result result = currentQuiz.provideAnswer(sc.next());
            switch (result) {
                case OK -> {
                    System.out.println("Правильно!");
                }
                case WRONG -> {
                    System.out.println("Неправильно. Правильный ответ: " + currentQuiz.getAnswer());
                }
                case INCORRECT_INPUT -> {
                    System.out.println("Ввод некорректный, попробуйте еще раз:");
                }
            }
        }
        System.out.println("Тест завершен. Оценка за тест : " + currentQuiz.getMark());
        System.out.println("Правильных ответов : " + currentQuiz.getCorrectAnswerNumber());
        System.out.println("Неправильных ответов : " + currentQuiz.getWrongAnswerNumber());
        System.out.println("Некорректных вводов : " + currentQuiz.getIncorrectInputNumber());
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        ExpressionTaskGenerator expressionTaskGenerator = new ExpressionTaskGenerator(
                0, 10, 1, EnumSet.allOf(MathTask.Operation.class));

        EquationTaskGenerator equationTaskGenerator = new EquationTaskGenerator(
                0, 10, 0, EnumSet.allOf(MathTask.Operation.class));

        ApplesTaskGenerator applesTaskGenerator = new ApplesTaskGenerator(0, 10);

        List<Task> animalsTaskPool = List.of(
                new TextTask("Сколько ног у осминога?", "2"),
                new TextTask("Сколько щупалец у осминога?", "8"),
                new TextTask("Сколько сердец у осминога?", "3"),
                new TextTask("Сколько мозгов у осминога?", "1"),
                new TextTask("Сколько глаз у осминога?", "2"),
                new TextTask("Сколько кг весил самый большой пойманный осминог?", "180"),
                new TextTask("Сколько существует видов пингвинов?", "18"),
                new TextTask("На сколько сотен метров в глубину максимально может нырнуть императорский пингвин?", "5"),
                new TextTask("Сколько метров размах крыльев у альбатроса?", "3"),
                new TextTask("Сколько глаз у пчелы?", "5"));

        PoolTaskGenerator poolTaskGenerator = new PoolTaskGenerator(false, animalsTaskPool);

        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(
                expressionTaskGenerator, equationTaskGenerator, poolTaskGenerator, applesTaskGenerator);

        quizMap.put("Expressions", new Quiz(expressionTaskGenerator, 10));
        quizMap.put("Equations", new Quiz(equationTaskGenerator, 10));
        quizMap.put("DifferentTasks", new Quiz(groupTaskGenerator, 10));
        quizMap.put("Animal facts", new Quiz(new PoolTaskGenerator(false, animalsTaskPool), 10));
        quizMap.put("ApplesTasks", new Quiz(applesTaskGenerator, 10));

        return quizMap;
    }
}
