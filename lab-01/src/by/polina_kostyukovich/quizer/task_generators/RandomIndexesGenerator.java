package by.polina_kostyukovich.quizer.task_generators;

import java.util.Random;

class RandomIndexesGenerator {
    static int[] getRandomIndexes(int size) {
        int[] indexes = new int[size];
        for (int i = 0; i < size; ++i) {
            indexes[i] = i;
        }

        Random rand = new Random();
        for (int i = 0; i < indexes.length; ++i) {
            int randomIndexToSwap = rand.nextInt(indexes.length);
            int temp = indexes[randomIndexToSwap];
            indexes[randomIndexToSwap] = indexes[i];
            indexes[i] = temp;
        }
        return indexes;
    }
}
