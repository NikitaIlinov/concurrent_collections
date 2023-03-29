import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static utils.Utils.*;


public class Main {

    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {

        Thread textGenerator = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String text = generateText("abc", 100000);
                try {
                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        textGenerator.start();

        Thread a = new Thread(() -> {
            char letter = 'a';
            int maxA = findMaxCharCount(queueA, letter);
            System.out.println("Max qty of " + letter + " int all texts: " + maxA);
        });
        a.start();

        Thread b = new Thread(() -> {
            char letter = 'b';
            int maxB = findMaxCharCount(queueB, letter);
            System.out.println("Max qty of " + letter + " int all texts: " + maxB);
        });
        b.start();

        Thread c = new Thread(() -> {
            char letter = 'c';
            int maxC = findMaxCharCount(queueC, letter);
            System.out.println("Max qty of " + letter + " int all texts: " + maxC);
        });
        c.start();

        a.join();
        b.join();
        c.join();
    }
}