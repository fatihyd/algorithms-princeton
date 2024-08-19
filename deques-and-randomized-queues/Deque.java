import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> first;
    private Node<Item> last;

    private class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<Item>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<Item>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item result = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        size--;

        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item result = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        size--;

        return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void print() {
        Node<Item> current = first;
        while (current != null) {
            StdOut.print(current.item + " ");
            current = current.next;
        }
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addLast(5);
        d.print();
        d.addLast(10);
        d.print();
        d.addFirst(15);
        d.print();
        d.removeFirst();
        d.print();
        d.removeLast();
        d.print();
        StdOut.println(d.isEmpty());
        StdOut.println(d.size());
    }
}
