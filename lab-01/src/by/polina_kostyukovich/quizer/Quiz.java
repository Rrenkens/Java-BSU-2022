package by.polina_kostyukovich.quizer;

import by.polina_kostyukovich.quizer.exceptions.BadGeneratorException;
import by.polina_kostyukovich.quizer.exceptions.BadTaskException;
import by.polina_kostyukovich.quizer.exceptions.QuizNotFinishedException;
import by.polina_kostyukovich.quizer.task_generators.GroupTaskGenerator;
import by.polina_kostyukovich.quizer.task_generators.PoolTaskGenerator;
import by.polina_kostyukovich.quizer.tasks.Task;
import by.polina_kostyukovich.quizer.tasks.TextTask;
import by.polina_kostyukovich.quizer.tasks.math_tasks.EquationTask;
import by.polina_kostyukovich.quizer.tasks.math_tasks.ExpressionTask;
import by.polina_kostyukovich.quizer.tasks.math_tasks.MathTask;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private final int taskCount;
    private final Task.Generator generator;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private Task currentTask;
    private boolean wasLastInputIncorrect = false;
    private boolean generatorThrewException;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(Task.Generator generator, int taskCount) {
        this.taskCount = taskCount;
        this.generator = generator;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() {
        if (!wasLastInputIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result) {
            case OK -> {
                ++correctAnswerNumber;
                wasLastInputIncorrect = false;
            }
            case WRONG -> {
                ++wrongAnswerNumber;
                wasLastInputIncorrect = false;
            }
            case INCORRECT_INPUT -> {
                ++incorrectInputNumber;
                wasLastInputIncorrect = true;
            }
        }
        return result;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return wrongAnswerNumber + correctAnswerNumber == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    public double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinishedException();
        }
        if (taskCount == 0) {
            return 1;
        }
        return ((double) correctAnswerNumber) / taskCount;
    }

    public boolean generatorThrewException() {
        return generatorThrewException;
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String},
     * значение - сам тест       {@link Quiz}
     */
    public static Map<String, Quiz> getQuizMap() throws IOException, ParseException {
        HashMap<String, Quiz> quizMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONArray quizzes = (JSONArray) parser.parse(new FileReader("src/by/polina_kostyukovich/quizer/jsons/quizzes.json"));
        for (Object quizObj : quizzes) {
            JSONObject quiz = (JSONObject) quizObj;
            String quizName = (String) quiz.get("QuizName");
            String generatorType = (String) quiz.get("GeneratorType");
            int taskCount = (int) (long) quiz.get("TaskCount");
            try {
                switch (generatorType) {
                    case "TextTaskGenerator" -> quizMap.put(quizName, new Quiz(getTextTaskGenerator(quiz), taskCount));
                    case "ExpressionTaskGenerator" -> quizMap.put(quizName, new Quiz(getExpressionTaskGenerator(quiz), taskCount));
                    case "EquationTaskGenerator" -> quizMap.put(quizName, new Quiz(getEquationTaskGenerator(quiz), taskCount));
                    case "GroupTaskGenerator" -> quizMap.put(quizName, new Quiz(getGroupTaskGenerator(quiz), taskCount));
                    case "PoolTaskGenerator" -> quizMap.put(quizName, new Quiz(getPoolTaskGenerator(quiz), taskCount));
                    default -> throw new BadGeneratorException("Unknown type of generator");
                }
            } catch (IllegalArgumentException | BadGeneratorException | BadTaskException e) {
                Quiz bad_quiz = new Quiz(null, taskCount);
                bad_quiz.generatorThrewException = true;
                quizMap.put(quizName, bad_quiz);
            }
        }
        return quizMap;
    }

    private static TextTask.Generator getTextTaskGenerator(JSONObject quiz) {
        Object[] tasksObjectArray = ((JSONArray) quiz.get("Tasks")).toArray();
        TextTask[] tasks = new TextTask[tasksObjectArray.length];
        for (int i = 0; i < tasksObjectArray.length; ++i) {
            tasks[i] = getTextTask((JSONObject) tasksObjectArray[i]);
        }
        return new TextTask.Generator(tasks);
    }

    private static ExpressionTask.Generator getExpressionTaskGenerator(JSONObject quiz) {
        int minNumber = (int) (long) quiz.get("MinNumber");
        int maxNumber = (int) (long) quiz.get("MaxNumber");
        return new ExpressionTask.Generator(minNumber, maxNumber, getOperations(quiz));
    }

    private static EquationTask.Generator getEquationTaskGenerator(JSONObject quiz) {
        int minNumber = (int) (long) quiz.get("MinNumber");
        int maxNumber = (int) (long) quiz.get("MaxNumber");
        return new EquationTask.Generator(minNumber, maxNumber, getOperations(quiz));
    }

    private static GroupTaskGenerator getGroupTaskGenerator(JSONObject quiz) {
        Object[] generatorsObjectArray = ((JSONArray) quiz.get("Generators")).toArray();
        Task.Generator[] generators = new Task.Generator[generatorsObjectArray.length];
        for (int i = 0; i < generatorsObjectArray.length; ++i) {
            JSONObject generator = (JSONObject) generatorsObjectArray[i];
            switch ((String) generator.get("GeneratorType")) {
                case "TextTaskGenerator" -> generators[i] = getTextTaskGenerator(generator);
                case "ExpressionTaskGenerator" -> generators[i] = getExpressionTaskGenerator(generator);
                case "EquationTaskGenerator" -> generators[i] = getEquationTaskGenerator(generator);
                case "GroupTaskGenerator" -> generators[i] = getGroupTaskGenerator(generator);
                case "PoolTaskGenerator" -> generators[i] = getPoolTaskGenerator(generator);
                default -> throw new BadGeneratorException("Unknown type of generator");
            }
        }
        return new GroupTaskGenerator(generators);
    }

    private static PoolTaskGenerator getPoolTaskGenerator(JSONObject quiz) {
        boolean allowDuplicate = (boolean) quiz.get("AllowDuplicate");
        Object[] tasksObjectArray = ((JSONArray) quiz.get("Tasks")).toArray();
        Task[] tasks = new Task[tasksObjectArray.length];
        for (int i = 0; i < tasksObjectArray.length; ++i) {
            JSONObject task = (JSONObject) tasksObjectArray[i];
            switch ((String) task.get("TaskType")) {
                case "TextTask" -> tasks[i] = getTextTask(task);
                case "ExpressionTask" -> tasks[i] = getExpressionTask(task);
                case "EquationTask" -> tasks[i] = getEquationTask(task);
                default -> throw new BadTaskException("Unknown type of task");
            }
        }
        return new PoolTaskGenerator(allowDuplicate, tasks);
    }

    private static MathTask.Operation getOperationFromString(String operation) {
        switch (operation) {
            case "Sum" -> {
                return MathTask.Operation.SUM;
            }
            case "Difference" -> {
                return MathTask.Operation.DIFFERENCE;
            }
            case "Multiplication" -> {
                return MathTask.Operation.MULTIPLICATION;
            }
            case "Division" -> {
                return MathTask.Operation.DIVISION;
            }
            default -> throw new BadTaskException("Unknown operation");
        }
    }

    private static EnumSet<MathTask.Operation> getOperations(JSONObject quiz) {
        Object[] operationsObjectArray = ((JSONArray) quiz.get("Operations")).toArray();
        EnumSet<MathTask.Operation> operations = EnumSet.noneOf(MathTask.Operation.class);
        for (Object operation : operationsObjectArray) {
            operations.add(getOperationFromString((String) operation));
        }
        return operations;
    }

    private static TextTask getTextTask(JSONObject task) {
        String taskText = (String) task.get("Text");
        try {
            String taskAnswer = (String) task.get("Answer");
            return new TextTask(taskText, taskAnswer);
        } catch (ClassCastException ignored) {}
        try {
            int taskAnswer = (int) (long) task.get("Answer");
            return new TextTask(taskText, taskAnswer);
        } catch (ClassCastException ignored) {}
        throw new BadTaskException("Invalid type of answer");
    }

    private static ExpressionTask getExpressionTask(JSONObject task) {
        int number1 = (int) (long) task.get("Number1");
        int number2 = (int) (long) task.get("Number2");
        MathTask.Operation operation = getOperationFromString(task.get("Operation").toString());
        return new ExpressionTask(number1, number2, operation);
    }

    private static EquationTask getEquationTask(JSONObject task) {
        int number1 = (int) (long) task.get("Number1");
        int number2 = (int) (long) task.get("Number2");
        MathTask.Operation operation = getOperationFromString(task.get("Operation").toString());
        boolean isXOnFirstPosition = (boolean) task.get("IsXOnFirstPosition");
        return new EquationTask(number1, number2, operation, isXOnFirstPosition);
    }
}
