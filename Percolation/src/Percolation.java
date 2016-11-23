import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private boolean[] opened;
    private int[] id;
    private int n;
    private int[] size;


    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = n;
        opened = new boolean[n * n];
        id = new int[n * n];
        size = new int[n * n];

        int index;

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                index = this.getId(j, i);
                if (j == 1) {
                    id[index] = 0;
                } else if (j == n) {
                    id[index] = n * n - 1;
                } else {
                    id[index] = index;
                    size[index] = 1;
                }
                opened[index] = false;
            }
        }
        size[0] = n;
        size[n * n - 1] = n;
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
        if (checkValid(row - 1, col - 1) && isOpen(row - 1, col - 1)) {
            union(row, col, row - 1, col - 1);
        }
        if (checkValid(row + 1, col - 1) && isOpen(row + 1, col - 1)) {
            union(row, col, row + 1, col - 1);
        }
        if (checkValid(row + 1, col + 1) && isOpen(row + 1, col + 1)) {
            union(row, col, row + 1, col + 1);
        }
        if (checkValid(row - 1, col + 1) && isOpen(row - 1, col + 1)) {
            union(row, col, row - 1, col + 1);
        }
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
        return isOpen(row, col) && (root(row, col) == root(1, 1));
    }

    public boolean percolates() {
        return isFull(n, n);
    }

    private int root(int row, int col) {
        int i = getId(row, col);
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private void union(int row1, int col1, int row2, int col2) {
        if (!checkValid(row1, col1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!checkValid(row2, col2)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int root1 = root(row1, col1);
        int root2 = root(row2, col2);
        if (root1 == root2) return;

        if (size[root1] > size[root2]) {
            id[root2] = root1;
            size[root1] += size[root2];
        } else {
            id[root1] = root2;
            size[root2] += size[root1];
        }
    }

    public static void main(String[] args) {
        System.out.print("start!");

        Percolation pt = new Percolation(100);
        assert !pt.isFull(1, 1);
        pt.open(2, 1);
        assert !pt.isFull(1, 1);
        assert !pt.isFull(2, 1);
        pt.open(1, 1);
        assert pt.isFull(2, 1);

        Percolation p = new Percolation(100);
        int i = 0;
        while (!p.percolates()) {
            int row = StdRandom.uniform(100) + 1;
            int col = StdRandom.uniform(100) + 1;
            if (p.isOpen(row, col)) {
                continue;
            }
            i += 1;
            p.open(row, col);
        }
        System.out.println("total count: " + Integer.toString(i));
    }

}

