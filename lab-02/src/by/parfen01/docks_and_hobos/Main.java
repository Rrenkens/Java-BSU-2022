package by.parfen01.docks_and_hobos;

import by.parfen01.docks_and_hobos.control.Controller;
import by.parfen01.docks_and_hobos.control.ProjectInitializer;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        String path = args[0];
        // я знаю, что нулевой аргуент это обычно вся строка, а аргументы для команды начинаются с 1,
        // просто в идее проще сразу прописать внутри программы путь нулевым аргументом
        Controller controller = ProjectInitializer.initController(path);
        controller.start();
        sleep(10 * 1000L);
        controller.stop();
        sleep(10 * 1000L);
        controller.start();
        sleep((500 * 1000L));
        controller.stop();
    }
}