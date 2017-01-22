package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
public class PercolationStats {
	private double[] pThres;
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	{
		if (n <= 0 || trials <= 0) {throw new IllegalArgumentException("values of arguments should be greater than 0");}
		double[] pThres = new double[trials];
		for (int i = 0; i < trials; i++)
		{
			Percolation percolationGen = new Percolation(n);
			int Nopened = 0;
			while (!percolationGen.percolates()) 
			{
				int col;
				int row;
				do {								//find an unopened cell and open
					col = StdRandom.uniform(n) + 1;
					row = StdRandom.uniform(n) + 1;
				} while (percolationGen.isOpen(row, col));
				percolationGen.open(row, col);
				Nopened += 1;
			}
			pThres[i] = Nopened / (double) (n*n);	
		}
		this.pThres = pThres;
	}
	public double mean()                          // sample mean of percolation threshold
	{
		return StdStats.mean(pThres);
	}
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(pThres);
	}
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		return StdStats.mean(pThres)-1.96*StdStats.stddev(pThres)/Math.sqrt(pThres.length);
	}
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		return StdStats.mean(pThres)+1.96*StdStats.stddev(pThres)/Math.sqrt(pThres.length);		
	}
	public static void main(String[] args)
	{
		Stopwatch stopwatch = new Stopwatch(); 
		//int N = Integer.parseInt(args[0]);
        //int T = Integer.parseInt(args[1]);
		int N = 200;
		int T = 100;
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("the elapsed time is       "+stopwatch.elapsedTime());
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
