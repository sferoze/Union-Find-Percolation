public class PercolationStats {
	
	Percolation[] experiments;
	int N; 
	int T;
	
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {   
	   this.N = N;
	   this.T = T;
	   experiments = new Percolation[T];
	   
	   for (int i = 0; i < T; i++) {
		   
		   Percolation objectGrid = new Percolation();
		   objectGrid = objectGrid.performPercolation(N);
		   
		   experiments[i] = objectGrid;
	   }
	   
   }
   public double mean()                     // sample mean of percolation threshold
   {
	   double total = 0;
	   for (int i = 0; i < T; i++) {
		   total += this.experiments[i].siteOpenCount;
	   }

	   double average = total/T;
	   return average;
   }
   /*
   public double stddev()                   // sample standard deviation of percolation threshold
   {
	   
   }
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {
	   
   }
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
	   
   }
   */
   public static void main(String[] args)   // test client, described below
   {
	   PercolationStats finalTest = new PercolationStats(20, 120);
	   double average = finalTest.mean();
	   
	   StdOut.println("Average is: " + average);
	   
	   
   }
}
