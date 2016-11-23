import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] opened;
    private int n;
    private WeightedQuickUnionUF un;
    private int[] max;


    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = n;
//        The default value for the elements in a boolean[] is false.
        opened = new boolean[n * n];

        un = new WeightedQuickUnionUF(n * n);

        for (int i = 2; i <= n; i++) {
            un.union(0, getId(1, i));
        }

        max = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            max[i] = i;
        }
    }

    private boolean checkValid(int row, int col) {
        return row > 0 && row <= n && col <= n && col > 0;
    }

    private int getId(int row, int col) {
        if (!checkValid(row, col)) {
            System.out.println("error:" + row + col);
            throw new java.lang.IndexOutOfBoundsException();
        }
        return col + n * (row - 1) - 1;
    }

    public void open(int row, int col) {
        if (!checkValid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        opened[getId(row, col)] = true;
        int index = getId(row, col);
        if (checkValid(row, col - 1) && isOpen(row, col - 1)) {
            union(index, getId(row, col - 1));
        }
        if (checkValid(row, col + 1) && isOpen(row, col + 1)) {
            union(index, getId(row, col + 1));
        }
        if (checkValid(row + 1, col) && isOpen(row + 1, col)) {
            union(index, getId(row + 1, col));
        }
        if (checkValid(row - 1, col) && isOpen(row - 1, col)) {
            union(index, getId(row - 1, col));
        }
    }

    private void union(int p, int q) {
        int a = un.find(p);
        int b = un.find(q);
        un.union(p, q);
        max[un.find(p)] = Math.max(max[a], max[b]);
    }

    public boolean isOpen(int row, int col) {
        if (!checkValid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return opened[getId(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!checkValid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return isOpen(row, col) && un.connected(0, getId(row, col));
    }

    public boolean percolates() {
//        System.out.println(max[un.find(0)]);
        return max[un.find(0)] > n * (n - 1) - 1;
    }

    public static void main(String[] args) {
        System.out.println("start!");

        Percolation pt = new Percolation(50);
        System.out.println(pt.isFull(1, 48) + " sure be false");
        pt.open(1, 48);
        pt.percolates();
        System.out.println(pt.isFull(1, 48) + " sure be true");
        pt.open(3, 48);
        pt.percolates();
        System.out.println(pt.isFull(3, 48) + " sure be false");
        pt.open(2, 48);
        pt.percolates();
        System.out.println(pt.isFull(2, 48) + " sure be true");
        System.out.println(pt.isFull(3, 48) + " sure be true");

//        Percolation p = new Percolation(100);
//        int i = 0;
//        while (!p.percolates()) {
//            int row = StdRandom.uniform(100) + 1;
//            int col = StdRandom.uniform(100) + 1;
//            if (p.isOpen(row, col)) {
//                continue;
//            }
//            i += 1;
//            p.open(row, col);
//        }
//        System.out.println("total count: " + Integer.toString(i));
    }

}

