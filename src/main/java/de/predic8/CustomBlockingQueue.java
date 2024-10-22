package de.predic8;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {
    private final Queue<E> queue;
    private final int capacity;

    public CustomBlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(E e) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    @Override
    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        E item = queue.remove();
        notifyAll();
        return item;
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }

    @Override
    public synchronized boolean offer(E e) {
        if (queue.size() < capacity) {
            queue.add(e);
            notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public synchronized E poll() {
        if (!queue.isEmpty()) {
            E item = queue.remove();
            notifyAll();
            return item;
        }
        return null;
    }

    @Override
    public synchronized E peek() {
        return queue.peek();
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) {
        return offer(e);
    }

    @Override
    public E poll(long timeout, TimeUnit unit) {
        return poll();
    }

    @Override
    public int remainingCapacity() {
        return capacity - size();
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return drainTo(c, Integer.MAX_VALUE);
    }

    @Override
    public synchronized int drainTo(Collection<? super E> c, int maxElements) {
        int count = 0;
        while (!queue.isEmpty() && count < maxElements) {
            c.add(queue.remove());
            count++;
        }
        notifyAll();
        return count;
    }
}