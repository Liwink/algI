/**
 * Created by Liwink on 12/6/16.
 */

import java.lang.Math;
import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;

public class Board {
    private final int[][] blocks;
    private int dim;

    public Board(int[][] blocks) {
        if (blocks == null) throw new java.lang.NullPointerException();
        dim = blocks.length;
        this.blocks = blocks;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                result += getHamming(row, col, blocks[row][col]);
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                result += getManhattan(row, col, blocks[row][col]);
            }
        }
        return result;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        if (dim == 1) {
            return new Board(blocks);
        } else {
            if (blocks[0][0] != 0 && blocks[0][1] != 0) return new Board(switchBlocks(0, 0, 0, 1));
            else return new Board(switchBlocks(1, 0, 1, 1));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (that.dimension() != this.dimension()) return false;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                if (that.blocks[row][col] != this.blocks[row][col]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        int row = 0;
        int col = 0;
        // get 0
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < blocks[row].length; c++) {
                if (blocks[r][c] == 0) {
                    row = r;
                    col = c;
                }
            }
        }
        if (inside(row - 1) && inside(col)) {
            boards.enqueue(new Board(switchBlocks(row, col, row - 1, col)));
        }
        if (inside(row + 1) && inside(col)) {
            boards.enqueue(new Board(switchBlocks(row, col, row + 1, col)));
        }
        if (inside(row) && inside(col - 1)) {
            boards.enqueue(new Board(switchBlocks(row, col, row, col - 1)));
        }
        if (inside(row) && inside(col + 1)) {
            boards.enqueue(new Board(switchBlocks(row, col, row, col + 1)));
        }
        return boards;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private boolean inside(int i) {
        return 0 <= i && i < dim;
    }

    private int[][] switchBlocks(int ra, int ca, int rb, int cb) {
        int[][] nb = new int[dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                nb[row][col] = blocks[row][col];
            }
        }
        int tmp = nb[ra][ca];
        nb[ra][ca] = nb[rb][cb];
        nb[rb][cb] = tmp;
        return nb;
    }

    private int getHamming(int row, int col, int point) {
        if (point == 0) return 0;
        if (row * dim + col + 1 == point) return 0;
        else return 1;
    }

    private int getManhattan(int row, int col, int point) {
        if (point == 0) return 0;
        int rowP = point / dim;
        int colP = point - rowP * dim - 1;
        return Math.abs(row - rowP) + Math.abs(col - colP);
    }

}
