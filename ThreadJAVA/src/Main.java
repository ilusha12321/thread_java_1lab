import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of steps: ");
        int steps = input.nextInt(); // Зчитуємо кількість кроків
        System.out.print("Enter the number of threads: ");
        int numThreads = input.nextInt(); // Зчитуємо кількість потоків
        int permissionInterval = 10000; // Інтервал часу для виконання

        SummingThread[] threads = new SummingThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SummingThread(i, steps);
            threads[i].start(); // Запускаємо потік
        }

        try {
            Thread.sleep(permissionInterval); // Чекаємо деякий час
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].setRunning(false); // Завершуємо роботу потоків
        }
    }

    private static class SummingThread extends Thread {
        private final int id;
        private final int steps;
        private volatile boolean running = true;

        public SummingThread(int id, int steps) {
            this.id = id;
            this.steps = steps;
        }

        public void run() {
            double sum = 0;
            double count = 0;
            double current = 0;

            while (running) {
                sum += current;
                count++;
                current += steps;
            }

            System.out.printf("Thread %d: sum=%f count=%f\n", id + 1, sum, count);
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}
