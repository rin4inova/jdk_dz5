package Philosophers;

import java.util.concurrent.locks.Lock;
public class SilentPhilosopher implements Runnable {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private final int mealsToEat;
    private int mealsEaten = 0;

    public SilentPhilosopher(int id, Lock leftFork, Lock rightFork, int mealsToEat) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.mealsToEat = mealsToEat;
    }

    @Override
    public void run() {
        try {
            while (mealsEaten < mealsToEat) {
                think();
                eat();
            }
            System.out.println("Философ №" + id + " завершил приём пищи.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Философ №" + id + " был прерван.");
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Философ №" + id + " размышляет.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        // Определяем порядок захвата вилок для предотвращения взаимной блокировки
        Lock firstFork = leftFork;
        Lock secondFork = rightFork;

        if (id % 2 == 0) {
            // Философы с четными индексами берут сначала правую вилку
            firstFork = rightFork;
            secondFork = leftFork;
        }

        // Блокируем вилки
        firstFork.lock();
        secondFork.lock();
        try {
            mealsEaten++;
            System.out.println("Философ №" + id + " ест. Приём пищи № " + mealsEaten);
            Thread.sleep((long) (Math.random() * 1000));
        } finally {
            // Освобождаем вилки после еды
            secondFork.unlock();
            firstFork.unlock();
        }
    }
}
