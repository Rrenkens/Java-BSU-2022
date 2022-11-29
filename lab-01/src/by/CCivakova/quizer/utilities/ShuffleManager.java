package by.CCivakova.quizer.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleManager {
    public static final List<Integer> getRandomPermutation(int n) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        return indexes;
    }
}
