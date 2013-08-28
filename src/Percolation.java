public class Percolation {
	
	boolean isOpen;
	int objectNumber;
	Percolation[][] grid;
	WeightedQuickUnionUF uf;
	
	public Percolation()
	{
		this.isOpen = false;
	}
	
	public Percolation(int N)
	{
		// Here I create the grid and fill each spot with a Percolation object that is by default closed. 
		// Each object has a unique number to identify it, which is the counter variable
		 this.grid = new Percolation[N][N];
		 int counter = 0;
		 for (int i=0; i < N; i++) {
			 for (int j=0; j < N; j++) {
				 Percolation pObject = new Percolation();
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
	
	public boolean percolates()
	{
		// Check to see if any of the top row components are connected with the bottom row components. If so the systems percolates.
		boolean percolate = false;
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++) {
				if (this.uf.connected(this.grid[0][i].objectNumber, this.grid[9][j].objectNumber))
					percolate = true;
			}
			
		}
		
		return percolate;
	}
	
	void checkBottom(int i, int j)
	{
		if (this.grid[i+1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i+1][j].objectNumber);
			StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i+1][j].objectNumber);
		}
	}
	void checkTop(int i, int j)
	{
		if (this.grid[i-1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i-1][j].objectNumber);
			StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i-1][j].objectNumber);
		}
	}
	void checkLeft(int i, int j)
	{
		if (this.grid[i][j + 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j + 1].objectNumber);
			StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j + 1].objectNumber);
		}
		
	}
	void checkRight(int i, int j)
	{
		if (this.grid[i][j - 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j - 1].objectNumber);
			StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j - 1].objectNumber);
		}
	}
	
	
	public static void main(String[] args) {
		
		// N is the size of the grid N x N
		int N = 10;
		int Max = N-1;
		int Min = 0;
		
		// Instantiate a new Percolation grid
		Percolation objectGrid = new Percolation(N);
		//Instantiate a new QuickFind Object
		objectGrid.uf = new WeightedQuickUnionUF((N * N));

		// While the grid does not Percolate I keep opening squares and check for adjacent squares that are open
		while (!objectGrid.percolates())
		{
			// First I open a random square in the grid
			int i = Min + (int)(Math.random() * ((Max - Min) + 1));
			int j = Min + (int)(Math.random() * ((Max - Min) + 1));
			objectGrid.grid[i][j].isOpen = true;
			
			/*  After the random square is open, I check the squares above, below, left and right, to the current open one.
			 	If any of those squares are open as well, I call the union command to join the components 
			 	
			 	So if two adjacent squares are open I call union and print out which components where joined in union. 
			*/
		
			try {
			objectGrid.checkLeft(i, j);
			objectGrid.checkRight(i, j);
			objectGrid.checkBottom(i, j);
			objectGrid.checkTop(i, j);
			} catch (IndexOutOfBoundsException e) {
				//continue
			}
		}
		// When the system finally percolates, I print out how many individual components are left from the original 100
		System.out.println("The remaining components is: " + objectGrid.uf.count());
		System.out.println("");
		
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