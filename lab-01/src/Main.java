import by.parfen01.quiser.Quiz;
import by.parfen01.quiser.Result;
import by.parfen01.quiser.task_generators.GroupTaskGenerator;
import by.parfen01.quiser.task_generators.PoolTaskGenerator;
import by.parfen01.quiser.tasks.TextTask;
import by.parfen01.quiser.tasks.math_tasks.EquationTask;
import by.parfen01.quiser.tasks.math_tasks.ExpressionTask;
import by.parfen01.quiser.tasks.math_tasks.MathTask;
import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Formatter;

public class Main {
    public static void main(String[] argv) {
        Map<String, Quiz> givenQuizzes = getQuizMap();
        System.out.println("Enter test name...");
        Scanner scanner = new Scanner(System.in);
        Quiz currentQuiz;
        String nameOfQuiz = scanner.nextLine();
        while(true) {
            if (!givenQuizzes.containsKey(nameOfQuiz)) {
                System.out.println("Test not found. Please try another name...");
                nameOfQuiz = scanner.nextLine();
            } else {
                currentQuiz = givenQuizzes.get(nameOfQuiz);
                break;
            }
        }
        while (!currentQuiz.isFinished()) {
            System.out.println(currentQuiz.nextTask().getText());
            String answer = scanner.nextLine();
            Result result = currentQuiz.provideAnswer(answer);
            if (result == Result.OK) {
                System.out.println("Right");
                continue;
            }
            if (result == Result.WRONG) {
                System.out.println("Wrong");
                continue;
            }
            if (result == Result.INCORRECT_INPUT) {
                System.out.println("Incorrect input. Try once more");
            }
        }
        Formatter formatter = new Formatter();
        formatter.format("%.1f", currentQuiz.getMark());
        System.out.println("total correct answer number: " + currentQuiz.getCorrectAnswerNumber());
        System.out.println("total wrong answer number: " + currentQuiz.getWrongAnswerNumber());
        System.out.println("total incorrect input number: " + currentQuiz.getIncorrectInputNumber());
        System.out.println("your final mark is: " + formatter);
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> result = new TreeMap<>();
        ExpressionTask.Generator firstTaskGenerator = new ExpressionTask.Generator(
                1, 100, EnumSet.allOf(MathTask.Operation.class));
        Quiz firstQuiz = new Quiz(firstTaskGenerator, 10);
        result.put("Expression Test", firstQuiz);
        EquationTask.Generator secondTaskGenerator = new EquationTask.Generator(
                1, 100, EnumSet.allOf(MathTask.Operation.class), new int[]{0, 1});
        Quiz secondQuiz = new Quiz(secondTaskGenerator, 10);
        result.put("Equation Test", secondQuiz);
        TextTask firstTextTask = new TextTask("what the highest mountain in the world?", "Everest");
        TextTask secondTextTask = new TextTask("what the fastest mammal in the world?", "Cheetah");
        TextTask thirdTextTask = new TextTask("Лектор расписался в журнале." +
                " Там было 3 н-ки." +
                " Сколько студентов было на паре?", "Один");
        TextTask foursTextTask = new TextTask("what the fastest mammal in the world?", "Cheetah");
        PoolTaskGenerator thirdTaskGenerator = new PoolTaskGenerator(
                false,
                secondTextTask,
                thirdTextTask,
                foursTextTask,
                firstTextTask);
        PoolTaskGenerator foursTaskGenerator = new PoolTaskGenerator(
                true,
                secondTextTask,
                thirdTextTask,
                foursTextTask,
                firstTextTask);
        Quiz thirdQuiz = new Quiz(thirdTaskGenerator, 3);
        result.put("Text Test", thirdQuiz);
        Quiz foursQuiz = new Quiz(new GroupTaskGenerator(
                firstTaskGenerator,
                secondTaskGenerator,
                thirdTaskGenerator.clone()),
                10);
        result.put("Mixed Test", foursQuiz);
        Quiz fifthQuiz = new Quiz(foursTaskGenerator, 3);
        result.put("Text Test With Repeat", fifthQuiz);
        return result;
    }
}