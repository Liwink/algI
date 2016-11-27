/**
 * Created by Liwink on 11/26/16.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (oldfirst == null) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack  underflow");
        Item item = first.item;
        first = first.next;
        if (!isEmpty()) first.prev = null;
        else last = null;
        n--;
        return item;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        n++;
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;
        last = last.prev;
        if (last == null) first = null;
        else last.next = null;
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque d = new Deque<Integer> ();
//        d.addLast(0);
//        d.removeFirst();
//        System.out.println(d.isEmpty());
//        d.addFirst(4);
//        d.removeFirst();
//        System.out.println(d.isEmpty());
//        d.addLast(6);
//        d.removeLast();
//        System.out.println(d.isEmpty());
//        d.addFirst(8);
//        d.removeFirst();
//        System.out.println(d.isEmpty());
//        for (int i=0; i < 5; i++) {
//            d.addFirst(i);
//        }
//        for (int i=5; i < 10; i++) {
//            d.addLast(i);
//        }
//        while (!d.isEmpty()) {
//            System.out.println("First: " + d.removeFirst());
//            System.out.println("Last : " + d.removeLast());
//            System.out.println(d.size());
//        }
    }

}
