import by.kalllegar.quizer.Quiz;
import by.kalllegar.quizer.Result;
import by.kalllegar.quizer.task_generators.GroupTaskGenerator;
import by.kalllegar.quizer.task_generators.PoolTaskGenerator;
import by.kalllegar.quizer.tasks.TextTask;
import by.kalllegar.quizer.tasks.math_tasks.EquationTask;
import by.kalllegar.quizer.tasks.math_tasks.ExpressionTask;
import by.kalllegar.quizer.tasks.math_tasks.MathTask;
import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Formatter;

public class Main {
    public static void main(String[] argv) {
        Map<String, Quiz> givenQuizzes = getQuizMap();
        System.out.println("enter the name of the test:");
        Scanner scanner = new Scanner(System.in);
        Quiz currentQuiz;
        String nameOfQuiz = scanner.nextLine();
        while(true) {
            if (!givenQuizzes.containsKey(nameOfQuiz)) {
                System.out.println("The test was not found. Please try another name:");
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
                System.out.println("Incorrect input. Try again");
            }
        }
        Formatter formatter = new Formatter();
        formatter.format("%.1f", currentQuiz.getMark());
        System.out.println("correct answers number: " + currentQuiz.getCorrectAnswerCount());
        System.out.println("wrong answers number: " + currentQuiz.getWrongAnswerCount());
        System.out.println("incorrect inputs number: " + currentQuiz.getIncorrectInputNumber());
        System.out.println("mark: " + formatter);
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> result = new TreeMap<>();
        //ExpressionTest usage
        ExpressionTask.Generator firstTaskGenerator = new ExpressionTask.Generator(
                1, 100, 2, EnumSet.allOf(MathTask.Operation.class));
        Quiz firstQuiz = new Quiz(firstTaskGenerator, 10);
        result.put("ExpressionTest", firstQuiz);
        //EquationTest usage
        EquationTask.Generator secondTaskGenerator = new EquationTask.Generator(
                1, 100, 3, EnumSet.allOf(MathTask.Operation.class), new int[]{0, 1});
        Quiz secondQuiz = new Quiz(secondTaskGenerator, 10);
        result.put("EquationTest", secondQuiz);
        //TextTest usage
        TextTask firstTextTask = new TextTask("what the best array sorting asymptotics?", "O(nlogn)");
        TextTask secondTextTask = new TextTask("how many people are in our group?", "26");
        TextTask thirdTextTask = new TextTask("the asymptotics with which the insertion into the map is performed?", "O(1)");
        TextTask fourthTextTask = new TextTask("what the best array sorting asymptotics?", "O(nlogn)");
        PoolTaskGenerator thirdTaskGenerator = new PoolTaskGenerator(false, secondTextTask,
                thirdTextTask, fourthTextTask, firstTextTask);
        PoolTaskGenerator fourthTaskGenerator = new PoolTaskGenerator(true, secondTextTask,
                thirdTextTask, fourthTextTask, firstTextTask);
        Quiz thirdQuiz = new Quiz(thirdTaskGenerator, 3);
        result.put("TextTest", thirdQuiz);
        //MixedTest usage
        Quiz fourthQuiz = new Quiz(new GroupTaskGenerator(firstTaskGenerator, secondTaskGenerator,
                thirdTaskGenerator.clone()), 10);
        result.put("GroupTest", fourthQuiz);
        Quiz fifthQuiz = new Quiz(fourthTaskGenerator, 3);
        result.put("Text Test With Repetition", fifthQuiz);
        return result;
    }
}