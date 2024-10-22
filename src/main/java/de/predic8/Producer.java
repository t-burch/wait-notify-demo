package de.predic8;

import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                queue.put(i);
                System.out.println("Produced: " + i);
                sleep(1200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}