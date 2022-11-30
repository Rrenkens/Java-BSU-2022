package by.polina_kostyukovich.docks_and_hobos;

import by.polina_kostyukovich.docks_and_hobos.controllers.Controller;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }

        Controller controller = new Controller(args[0]);
        controller.startRunning();
    }
}
