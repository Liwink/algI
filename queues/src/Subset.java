/**
 * Created by Liwink on 11/27/16.
 */

import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);

        try {
            while (true) {
                q.enqueue(StdIn.readString());
            }
        } catch (NoSuchElementException e) {
        }

        for (int i = 0; i < k; i++) System.out.println(q.dequeue());

    }
}
