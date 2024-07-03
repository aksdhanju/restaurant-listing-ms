package multithreading.diningphilosophers;

import java.util.concurrent.locks.Lock;

class Philosopher implements Runnable {
    private final Lock leftFork;
    private final Lock rightFork;
    private final int philosopherNumber;

    public Philosopher(int philosopherNumber, Lock leftFork, Lock rightFork) {
        this.philosopherNumber = philosopherNumber;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println("Philosopher " + philosopherNumber + " is " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Thinking
                doAction("thinking");
                leftFork.lock();
                try {
                    doAction("picked up left fork");
                    rightFork.lock();
                    try {
                        // Eating
                        doAction("picked up right fork and starts eating");
                        doAction("put down right fork");
                    } finally {
                        rightFork.unlock();
                    }
                    doAction("put down left fork");
                } finally {
                    leftFork.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
