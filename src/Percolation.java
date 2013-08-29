public class Percolation {
	
	Spot[][] grid;
	WeightedQuickUnionUF uf;
	double siteOpenCount;
	
	public Percolation()
	{
	}
	
	public Percolation(int N)
	{
		// Here I create the grid and fill each spot with a Percolation object that is by default closed. 
		// Each object has a unique number to identify it, which is the counter variable
		 this.grid = new Spot[N][N];
		 int counter = 0;
		 for (int i=0; i < N; i++) {
			 for (int j=0; j < N; j++) {
				 Spot pObject = new Spot();
				 this.grid[i][j] = pObject;
				 this.grid[i][j].objectNumber = counter;
				 counter++;
			 }
		 }
	}
	
	public void open(int i, int j)
	{
		this.grid[i][j].isOpen = true;
	}
	
	public boolean isOpen(int i, int j)
	{
		if (this.grid[i][j].isOpen)
			return true;
		else
			return false;
	}
	
	public boolean isFull(int i, int j)
	{
		if (this.grid[i][j].isOpen)
			return false;
		else
			return true;
	}
	
	public boolean percolates(int n)
	{
		// Check to see if any of the top row components are connected with the bottom row components. If so the systems percolates.
	
		boolean percolate = false;
		if (this.uf.connected((n*n), ((n*n)+1))) {
			percolate = true;
		}
		
		return percolate;
	}
	
	void checkBottom(int i, int j)
	{
		if (this.grid[i+1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i+1][j].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i+1][j].objectNumber);
		}
	}
	void checkTop(int i, int j)
	{
		if (this.grid[i-1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i-1][j].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i-1][j].objectNumber);
		}
	}
	void checkLeft(int i, int j)
	{
		if (this.grid[i][j + 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j + 1].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j + 1].objectNumber);
		}
		
	}
	void checkRight(int i, int j)
	{
		if (this.grid[i][j - 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j - 1].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j - 1].objectNumber);
		}
	}
	
	Percolation performPercolation(int N)
	{
		// N is the size of the grid N x N
				//int N = 20;
				int Max = N-1;
				int Min = 0;

				// Instantiate a new Percolation grid
				Percolation objectGrid = new Percolation(N);
				//Keep track of how many sites have been open
				objectGrid.siteOpenCount = 0;
				//Instantiate a new QuickFind Object
				objectGrid.uf = new WeightedQuickUnionUF((N * N) + 2);
				
				// Create two virtual sites and connect them to the top and bottom row elements. The system percolates when the two virtual sites are connected
				Spot virtualsite1 = new Spot();
				virtualsite1.objectNumber = (N*N);
				virtualsite1.isOpen = true;
				Spot virtualsite2 = new Spot();
				virtualsite2.objectNumber = (N*N) + 1;
				virtualsite2.isOpen = true;
				
				for (int i=0; i < N; i++) {
					objectGrid.uf.union(virtualsite1.objectNumber, objectGrid.grid[0][i].objectNumber);
					objectGrid.uf.union(virtualsite2.objectNumber, objectGrid.grid[N-1][i].objectNumber);
				}
				
				// While the grid does not Percolate I keep opening squares and check for adjacent squares that are open
				while (!objectGrid.percolates(N))
				{
					// First I open a random square in the grid
					int i = Min + (int)(Math.random() * ((Max - Min) + 1));
					int j = Min + (int)(Math.random() * ((Max - Min) + 1));
					
					if (objectGrid.grid[i][j].isOpen != true) {
						objectGrid.grid[i][j].isOpen = true;
						objectGrid.siteOpenCount++;
					
					/*  After the random square is open, I check the squares above, below, left and right, to the current open one.
					 	If any of those squares are open as well, I call the union command to join the components 
					 	
					 	So if two adjacent squares are open I call union and print out which components where joined in union. 
					*/
				
						try {
							objectGrid.checkTop(i, j);
							} catch (IndexOutOfBoundsException e) {
							//continue
							}
						try {
							objectGrid.checkBottom(i, j);
							} catch (IndexOutOfBoundsException e) {
								//continue
							}
						try {
							objectGrid.checkLeft(i, j);
							} catch (IndexOutOfBoundsException e) {
								//continue
							}
						try {
							objectGrid.checkRight(i, j);
							} catch (IndexOutOfBoundsException e) {
								//continue
							}
					}
				}
				
				//StdOut.println("Number of open sites: " + objectGrid.siteOpenCount);
				
				return objectGrid;
	}
	
	public static void main(String[] args) {
		
		int N= 20;
		
		Percolation objectGrid = new Percolation();
		objectGrid = objectGrid.performPercolation(N);
		
		// When the system finally percolates, I print out how many individual components are left from the original 100
		System.out.println("The remaining components is: " + objectGrid.uf.count());
		
		System.out.println("");
		
		@SuppressWarnings("unused")
		Sites frame = new Sites("Percolation", objectGrid, N);

		
		// Prints out a visual of the final grid that percolates
		for (int i=0; i < N; i++){
			for (int j =0; j < N; j++) {
				if (objectGrid.grid[i][j].isOpen)
					System.out.print("| |");
				else
					System.out.print("|X|");
			}
			System.out.println("");
		}
	}
}