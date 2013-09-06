public class PercolationStats {
	
	private double[] siteOpenCount;
	private double N; 
	private double T;
	private double mean;
	private double stddev;
	
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {   
	   this.N = N;
	   this.T = T;
	   siteOpenCount = new double[T];
	   
	   for (int i = 0; i < T; i++) {
		   
		   Percolation objectGrid = new Percolation(N);
		   double openSites = performPerc (objectGrid, this.N);
		   
		   siteOpenCount[i] = openSites;
	   }
	   
   }
   
   private static double performPerc (Percolation objectGrid, double N)
   {
	   double Max = N;
	   int Min = 1;
	   int openSites = 0;
	   
	   while (!objectGrid.percolates())
		{
			// First I open a random square in the grid
			int i = Min + (int)(Math.random() * ((Max - Min) + 1));
			int j = Min + (int)(Math.random() * ((Max - Min) + 1));
			
			if (objectGrid.isOpen(i, j) != true) {
				objectGrid.open(i, j);
				openSites++;
			}
		}
	   
	   return openSites;

   }

   public double mean()                     // sample mean of percolation threshold
   {
	   //double total = 0;
	   double ratio_total = 0;

	   for (int i = 0; i < this.T; i++) {
		   ratio_total +=   siteOpenCount[i] / (this.N * this.N);
	   }

	   double average = ratio_total/this.T;
	   
	   this.mean = average;
	   
	   return average;
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
	   double stddev_total = 0;
	   for (int i = 0; i < this.T; i++) {
		   stddev_total +=   Math.pow(((siteOpenCount[i] / (this.N * this.N)) - mean()), 2);
	   }
	   double stddev = stddev_total/(this.T - 1);
	   
	   double stddev_solution = Math.sqrt(stddev);
	   
	   this.stddev = stddev_solution;
	   return stddev_solution;
   }
   
   
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {
	   double answer = mean() - ((1.96 * stddev()) / Math.sqrt(this.T));
	   
	   return answer;
   }
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
	   double answer = mean() + ((1.96 * stddev()) / Math.sqrt(this.T));
	   
	   return answer;
   }
   
   public static void main(String[] args)   // test client, described below
   {
	   PercolationStats finalTest = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	   double average = finalTest.mean();
	   double stddev = finalTest.stddev();
	   double confidenceLo = finalTest.confidenceLo();
	   double confidenceHi = finalTest.confidenceHi();
	   StdOut.println("mean                    = " + average);
	   StdOut.println("stddev                  = " + stddev);
	   StdOut.println("95% confidence interval = " + confidenceLo + ", " + confidenceHi);
	   
   }
}
