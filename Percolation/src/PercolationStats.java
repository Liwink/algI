/**
 * Created by Liwink on 11/23/16.
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int n;
    private double[] results;
    private double mean;
    private double stddev;
    private int trials;

    public PercolationStats(int num, int trials) {
        if (num <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        n = num;
        this.trials = trials;

        double total = n * n;

        results = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation p = new Percolation(n);

            int count = 0;

            while (!p.percolates()) {
                int row = StdRandom.uniform(100) + 1;
                int col = StdRandom.uniform(100) + 1;
                if (p.isOpen(row, col)) {
                    continue;
                }
                count += 1;
                p.open(row, col);
            }
            results[i] = count / total;
        }

        mean = this.mean();
        stddev = this.stddev();
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(num, t);
        System.out.println("mean = " + Double.toString(p.mean()));
        System.out.println("stddev = " + Double.toString(p.stddev()));
        System.out.println("95% confidence interval = " + Double.toString(p.confidenceLo())
                + ", " + Double.toString(p.confidenceHi()));
    }
}
