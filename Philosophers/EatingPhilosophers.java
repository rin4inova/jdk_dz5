package Philosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class EatingPhilosophers {
    private static final int NUMBERS_OF_PHILOSOPHERS = 5;
    private static final int NUMBER_OF_MEALS_PER_PHILOSOPHER = 3;

    private final SilentPhilosopher[] silentPhilosophers = new SilentPhilosopher[NUMBERS_OF_PHILOSOPHERS];
    private final Lock[] forks = new ReentrantLock[NUMBERS_OF_PHILOSOPHERS];

    public EatingPhilosophers() {
        for (int i = 0; i < NUMBERS_OF_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < NUMBERS_OF_PHILOSOPHERS; i++) {
            int leftFork = i;
            int rightFork = (i + 1) % NUMBERS_OF_PHILOSOPHERS;
            silentPhilosophers[i] = new SilentPhilosopher(i, forks[leftFork], forks[rightFork], NUMBER_OF_MEALS_PER_PHILOSOPHER);
            new Thread(silentPhilosophers[i]).start();
        }
    }
}
