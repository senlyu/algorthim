import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private double[] results;
    private int runs;

    public PercolationStats(int n, int trials){
        if ((n <= 0) || (trials <= 0)){
            throw new IllegalArgumentException("Dimensions must be positive.");
        }
        //perform trials independent experiments on an n-by-n grid
        results = new double[trials];
        runs = trials;
        for (int i = 0; i < trials; i++){
            int percolationcount = run(n);
            results[i] = (double)(percolationcount) / (n * n);
        }
    }

    private int run(int n){
        //TODO Auto-generated method stub
        Percolation percolation = new Percolation(n);
        int totalsize = n * n;
        int sitecount = 0;
        while (true){
            if (percolation.percolates()){
                break;
            }
            int randomnum = StdRandom.uniform(1, totalsize + 1);
            int col = ((randomnum - 1) % n + 1);
            int row = ((randomnum - col) / n + 1);
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
            }
        }
        return percolation.numberOfOpenSites();
    }

    public double mean(){
        //sample mean of percolation threshold
        return StdStats.mean(results);
    }

    public double stddev(){
        //sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }

    public double confidenceLo(){
        //low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(runs);
    }

    public double confidenceHi(){
        //high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(runs);
    }

    public static void main(String[] args){
        //test client (described below)
        int matrixsize = 0;
        int runnumber = 0;
        matrixsize = Integer.parseInt(args[0]);
        runnumber = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(matrixsize, runnumber);
        StdOut.println("mean \t\t\t= " + stats.mean());
        StdOut.println("stddev \t\t\t= " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}