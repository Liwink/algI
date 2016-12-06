/**
 * Created by Liwink on 12/6/16.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Solver {
    Board initial;
    Board twin;

    Try last = null;
    Try twinLast = null;

    MinPQ<Try> pq = new MinPQ<Try>();
    MinPQ<Try> tpq = new MinPQ<Try>();

    private class Try implements Comparable<Try> {
        private int step;
        private Board board;
        public Try preTry;

        public Try(Try preTry, Board board, int step) {
            this.board = board;
            this.preTry = preTry;
            this.step = step;
        }

        public int compareTo(Try that) {
            return (this.board.manhattan() + this.step)
                    - (that.board.manhattan() + that.step);
        }
    }

    public Solver(Board initial) {
        this.initial = initial;
        twin = initial.twin();

        pq.insert(new Try(null, initial, 0));
        tpq.insert(new Try(null, twin, 0));


        while (last == null && twinLast == null) {
            last = newTry(pq);
            twinLast = newTry(tpq);
        }
    }

    public boolean isSolvable() {
        return last != null;
    }

    public int moves() {
        if (last == null) return -1;
        return last.step;
    }

    public Iterator<Board> solution() {
        if (last == null) return null;
        Stack<Board> s = new Stack<Board>();
        Try t = last;
        while (t != null){
            s.push(t.board);
            t = t.preTry;
        }
        return s.iterator();
    }

    private Try newTry(MinPQ<Try> pq) {
        // is empty?
        Try t = pq.delMin();

        if (t.board.isGoal()) {
            return t;
        }

        Iterator iterator = t.board.neighbors();
        int s = t.step + 1;
        while (iterator.hasNext()) {
            Board b = (Board) iterator.next();
            if (t.preTry != null && t.preTry.board == b) continue;
            pq.insert(new Try(t, b, s));
        }
        return null;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            Iterator iterator = solver.solution();
            while (iterator.hasNext()) {
                Board board = (Board) iterator.next();
                StdOut.println(board);
            }
        }
    }

}
