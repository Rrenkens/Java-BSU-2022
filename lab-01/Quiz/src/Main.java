import by.AlexAzyavchikov.quizer.*;
import by.AlexAzyavchikov.quizer.task_generators.*;
import by.AlexAzyavchikov.quizer.task_generators.math_task_generators.*;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.TextTask;

import java.util.*;

import static by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        if (quizMap == null) {
            throw new RuntimeException("quizMap == null");
        }

        System.out.println("Available quizzes:");
        for (String quizName : quizMap.keySet()) {
            System.out.println("TEST:\t" + quizName);
        }
        System.out.println("");

        Quiz usingQuiz = null;
        System.out.println("Input test name, please:");
        Scanner scanner = new Scanner(System.in);
        String testName = scanner.next();
        while (!quizMap.containsKey(testName)) {
            System.out.println("Input CORRECT test name, please:");
            testName = scanner.next();
        }
        System.out.println("Using test: \"" + testName + "\"");
        usingQuiz = quizMap.get(testName);

        scanner.nextLine();
        while (!usingQuiz.isFinished()) {
            Task task = usingQuiz.nextTask();
            System.out.println(task.getText());
            String answer = scanner.nextLine();
            switch (usingQuiz.provideAnswer(answer)) {
                case OK -> {
                    System.out.println("OK");
                }
                case WRONG -> {
                    System.out.println("WRONG");
                }
                case INCORRECT_INPUT -> {
                    System.out.println("INCORRECT_INPUT, try again:");
                }
            }
        }
        System.out.println("Total mark: " + usingQuiz.getMark());
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
//        TODO (AZUAVCHIKOV)
        Map<String, Quiz> quizMap = new LinkedHashMap<>();
        //ExpressionTests
        {
            //tasks: a+b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.SUM), 0));
            quizMap.put("ExpressionSumTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a-b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIFFERENCE), 0));
            quizMap.put("ExpressionDifferenceTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a*b=?
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.MULTIPLICATION), 0));
            quizMap.put("ExpressionMultiplicationTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a/b=?
            /*
            Note that:
            x/0 = Infinity
            -x/0 = Infinity
            0/0 = NaN
             */
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new ExpressionMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIVISION), 0));
            quizMap.put("ExpressionDivisionTest", new Quiz(groupGenerator, 10));
        }
        //EquationTests
        {
            //tasks: a+x=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.SUM), 0));
            quizMap.put("EquationSumTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a-x=b or x-a=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIFFERENCE), 0));
            quizMap.put("EquationDifferenceTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a*x=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.MULTIPLICATION), 0));
            quizMap.put("EquationMultiplicationTest", new Quiz(groupGenerator, 10));
        }
        {
            //tasks: a/x=b or x/a=b
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new EquationMathTaskGenerator(-5, 5, EnumSet.of(Operator.DIVISION), 0));
            quizMap.put("EquationDivisionTest", new Quiz(groupGenerator, 10));
        }
        //TextTests
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(false,
                            new TextTask("print lol", "lol"),
                            new TextTask("Print lol", "lol"),
                            new TextTask("print lol", "lol"),
                            new TextTask("print lol", "lol"),
                            new TextTask("print kek", "kek")));
            quizMap.put("TextTest", new Quiz(groupGenerator, 3));
        }
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(true,
                            new TextTask("print lol", "lol"),
                            new TextTask("print lol", "kek")));
            quizMap.put("TrickyTextTest", new Quiz(groupGenerator, 5));
        }
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(false,
                            new TextTask("Файл-сервер, файловый сервер", "file server"),
                            new TextTask("Мост", "bridge"),
                            new TextTask("маршрутизатор", "router"),
                            new TextTask("магистраль/основа", "backbone"),
                            new TextTask("локальная сеть", "LAN"),
                            new TextTask("шлюз", "gateway"),
                            new TextTask("Модем", "modem"),
                            new TextTask("витая пара (кабель)", "twisted-pair cable"),
                            new TextTask("Разделять", "to partition"),
                            new TextTask("Разнородный (different; unlike)", "dissimilar"),
                            new TextTask("Передача", "transmission"),
                            new TextTask("Сетевая карта/плата", "network interface card"),
                            new TextTask("Подключать", "plug-in"),
                            new TextTask("Точка доступа(я тебя по ... вычислю)", "access point"),
                            new TextTask("Беспроводная локальная сеть", "WLAN"),
                            new TextTask("центр", "hub"),
                            new TextTask("Tочка доступа", "hotspot"),
                            new TextTask("Мбит/сек", "mbps"),
                            new TextTask("помехи, вмешательство", "interference"),
                            new TextTask("перехватывать, перехватить", "intercept"),
                            new TextTask("алгоритм для обеспечения безопасности сетей Wi-Fi.", "wired equivalent privacy"),
                            new TextTask("малофункциональный, маломощный сетевой клиент-терминал (компьютер)", "thin client"),
                            new TextTask("асимметричная цифровая абонентская линия", "asymmetric digital subscriber line"),
                            new TextTask("Уровень приложений/прикладной уровень", "application layer"),
                            new TextTask("заголовок", "header"),
                            new TextTask("Представительский уровень", "presentation layer"),
                            new TextTask("Американский Стандартный Код Информационного Обмена", "ASCII"),
                            new TextTask("сжимать, сдавливать", "compress"),
                            new TextTask("Сеансовый уровень", "session layer"),
                            new TextTask("узел сети", "node"),
                            new TextTask("границы, пределы", "boundaries"),
                            new TextTask("полудуплексный режим", "half-duplex"),
                            new TextTask("транспортный уровень", "transport layer"),
                            new TextTask("контрольная сумма", "check sum"),
                            new TextTask("Перемешать", "scramble"),
                            new TextTask("сетевой уровень", "network layer"),
                            new TextTask("канальный уровень", "data link layer"),
                            new TextTask("Физический уровень", "physical layer"),
                            new TextTask("среда, средство/носитель", "medium"),
                            new TextTask("скопление, перегруженность, затор, дорожная \"пробка\"", "congestion"),
                            new TextTask("символ", "character")
//                            new TextTask("print", "kek"),
                    ));
            quizMap.put("EnglishWordsTest1", new Quiz(groupGenerator, 41));
//            quizMap.put("EnglishWordsTest", new Quiz(groupGenerator, 8));
        }
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new PoolTaskGenerator(false,
                            new TextTask("расширение", "extension"),
                            new TextTask("развлекательный, относящийся к сфере развлечений", "recreational"),
                            new TextTask("удаленный сервер", "remote server"),
                            new TextTask("унифицированный определитель местонахождения ресурса", "uniform resource locator"),
                            new TextTask("предоставлять, обеспечивать", "provide"),
                            new TextTask("делать закладку", "bookmark"),
                            new TextTask("замедлять", "slowdown"),
                            new TextTask("вводить в", "feed into"),
                            new TextTask("прерывание", "interruption"),
                            new TextTask("режим", "routine"),
                            new TextTask("иметь дело с", "deal with"),
                            new TextTask("полоса пропускания", "bandwidth"),
                            new TextTask("мудро, разумно", "wisely"),
                            new TextTask("исключать, не допускать", "exclude"),
                            new TextTask("Сузить/сводить к меньшему", "narrow down"),
                            new TextTask("соответствующий", "relevant"),
                            new TextTask("электронный ключ", "dongle"),
                            new TextTask("настраивать, регулировать", "finetune"),
                            new TextTask("случай, происшествие", "occurrence"),
                            new TextTask("подмена", "spoofing"),
                            new TextTask("строго; сурово; жёстко", "rigidly"),
                            new TextTask("простой протокол электронной почты", "simple mail transfer protocol"),
                            new TextTask("прямой, откровенный", "straightforward"),
                            new TextTask("возможность", "facility"),
                            new TextTask("пакетный режим", "batch mode"),
                            new TextTask("отменять, аннулировать", "cancel"),
                            new TextTask("извлекать", "retrieve"),
                            new TextTask("язык гипертекстовой разметки", "hypertext markup language"),
                            new TextTask("интернет-провайдер", "internet service provider"),
                            new TextTask("предварительно устанавливать", "preset"),
                            new TextTask("снять, забрать", "pick up"),
                            new TextTask("очистить, опорожнить", "clean out"),
                            new TextTask("наполнять, заполнять", "fill up"),
                            new TextTask("изначально, первоначально, сначала", "initially"),
                            new TextTask("тема", "subject"),
                            new TextTask("заголовок", "header"),
                            new TextTask("инициировать,начать,возбудить,запустить", "initiate")
//                            new TextTask("print", "kek"),
                    ));
            quizMap.put("EnglishWordsTest2", new Quiz(groupGenerator, 37));
//            quizMap.put("EnglishWordsTest2", new Quiz(groupGenerator, 5));
        }
        //MyTextTest
        {
            GroupTaskGenerator groupGenerator = new GroupTaskGenerator(
                    new MyTaskGenerator(10));
            quizMap.put("CringeTextTest", new Quiz(groupGenerator, 5));
        }
        return quizMap;
    }
}