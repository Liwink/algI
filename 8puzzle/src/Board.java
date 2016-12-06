/**
 * Created by Liwink on 12/6/16.
 */

import java.lang.Math;
import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;

public class Board {
    private int[][] blocks;
    private int dim;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        dim = blocks.length;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[0].length; col++) {
                result += getHamming(row, col, blocks[row][col]);
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[0].length; col++) {
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
            return new Board(switchBlocks(0, 0, 0, 1));
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
            for (int col = 0; col < blocks[0].length; col++) {
                if (that.blocks[row][col] != this.blocks[row][col]) return false;
            }
        }
        return true;
    }

    public Iterator<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        int row = 0;
        int col = 0;
        // get 0
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < blocks[0].length; c++) {
                if (blocks[r][c] == 0) {
                    row = r;
                    col = c;
                }
            }
        }
        for (int r = row-1; r< row+2; r++) {
            for (int c = col - 1; c < col + 2; c++) {
                if (!inside(r) || !inside(c)) continue;
                boards.enqueue(new Board(switchBlocks(row, col, r, c)));
            }
        }
        return boards.iterator();
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
        return 0 <= i && i <= dim;
    }

    private int[][] switchBlocks(int ra, int ca, int rb, int cb) {
        int[][] nb = new int[dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < blocks[0].length; col++) {
                nb[row][col] = blocks[row][col];
            }
        }
        int tmp = blocks[ra][ca];
        blocks[ra][ca] = blocks[rb][cb];
        blocks[rb][cb] = tmp;
        return blocks;
    }

    private int getHamming(int row, int col, int point) {
        if (row * dim + col == point) return 0;
        else return 1;
    }

    private int getManhattan(int row, int col, int point) {
        int rowP = point / dim;
        int colP = point - rowP * dim;
        return Math.abs(row - rowP) + Math.abs(col - colP);
    }

}
