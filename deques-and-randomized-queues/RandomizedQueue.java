import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniformInt(0, size);
        Item randomItem = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(0, size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices;
        private int currentIndex;

        public RandomizedQueueIterator() {
            indices = new int[size];
            currentIndex = 0;
            for (int i = 0; i < size; i++) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[indices[currentIndex++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    private void print() {
        for (int i = 0; i < size; i++) {
            StdOut.print(items[i] + " ");
        }
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQ = new RandomizedQueue<>();
        rQ.enqueue(5);
        rQ.print();
        rQ.enqueue(10);
        rQ.print();
        rQ.enqueue(15);
        rQ.print();
        rQ.dequeue();
        rQ.print();
        rQ.dequeue();
        rQ.print();
        StdOut.println(rQ.isEmpty());
        StdOut.println(rQ.size());
    }
}
