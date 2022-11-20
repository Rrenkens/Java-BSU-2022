package by.toharrius.quizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link by.toharrius.quizer.Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        return null;
    }

    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        for (;;) {
            var str = br.readLine();
            System.out.println(str);
        }
    }
}
