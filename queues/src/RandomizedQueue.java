/**
 * Created by Liwink on 11/27/16.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        shuffle(a, n);
        Item item = a[n-1];
        n--;
        a[n] = null;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    public Item sample() {
        return a[StdRandom.uniform(n)];
    }

    private void shuffle(Item[] ns, int count) {
        if (ns == null) throw new IllegalArgumentException("argument array is null");
        if (count <= 1) return;
        for (int i = 0; i < count; i++) {
            int r = i + StdRandom.uniform(count- i);     // between i and n-1
            Item temp = ns[i];
            ns[i] = ns[r];
            ns[r] = temp;
        }
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i;

        public ArrayIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i++];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        int n = 10;
//        int[] ns = new int[n];
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < n; i++) {
            System.out.println("sample: " + q.sample());
        }
        for (int i = 0; i < 4; i++) {
            System.out.println("dequeue: " + q.dequeue());
        }
//        this.shuffle(ns, 1, 8);
//        System.out.println(ns);
        for (int i : q
                ) {
            System.out.print(i);
        }
    }
}
