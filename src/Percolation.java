public class Percolation {
	
	private class Spot {
		boolean isOpen;
		int objectNumber;
		
		private Spot() {
			this.isOpen = false;
		}

	}
	
	private Spot[][] grid;
	private WeightedQuickUnionUF uf;
	private int N;
	private Spot virtualsite1;
	private Spot virtualsite2;
	
	
	public Percolation(int N)
	{
		// Here I create the grid and fill each spot with a Percolation object that is by default closed. 
		// Each object has a unique number to identify it, which is the counter variable
		this.N = N;
		
		 this.grid = new Spot[this.N][this.N];
		 int counter = 0;
		 for (int i=0; i < this.N; i++) {
			 for (int j=0; j < this.N; j++) {
				 Spot pObject = new Spot();
				 this.grid[i][j] = pObject;
				 this.grid[i][j].objectNumber = counter;
				 counter++;
			 }
		 }
		 
		 this.uf = new WeightedQuickUnionUF((this.N * this.N) + 2);
		 	virtualsite1 = new Spot();
			virtualsite1.objectNumber = (this.N*this.N);
			virtualsite1.isOpen = true;
			virtualsite2 = new Spot();
			virtualsite2.objectNumber = (this.N*this.N) + 1;
			virtualsite2.isOpen = true;
			
			for (int i=0; i < this.N; i++) {
				this.uf.union(virtualsite1.objectNumber, this.grid[0][i].objectNumber);
				this.uf.union(virtualsite2.objectNumber, this.grid[this.N-1][i].objectNumber);
			}
		 
		
	}
	
	public void open(int i, int j)
	{
			i = i-1;
			j = j-1;
		
		try {
			this.grid[i][j].isOpen = true;
			} catch (IndexOutOfBoundsException e) {
			//continue
			}
		
		
		try {
			this.checkTop(i, j);
			} catch (IndexOutOfBoundsException e) {
			//continue
			}
		try {
			this.checkBottom(i, j);
			} catch (IndexOutOfBoundsException e) {
				//continue
			}
		try {
			this.checkLeft(i, j);
			} catch (IndexOutOfBoundsException e) {
				//continue
			}
		try {
			this.checkRight(i, j);
			} catch (IndexOutOfBoundsException e) {
				//continue
			}
	}
	
	public boolean isOpen(int i, int j)
	{
		i = i-1;
		j = j-1;
		
		if (this.grid[i][j].isOpen)
			return true;
		else
			return false;
	}
	
	public boolean isFull(int i, int j)
	{
		i = i-1;
		j= j-1;
		
		// I am really confused about the requirements of this method. It is not supposed to be the inverse of isOpen
		/*
		if (this.grid[i][j].isOpen)
			return true;
		else
			return false;
		 */
		if (!this.grid[i][j].isOpen)
			return false;
		
		if (this.uf.connected((virtualsite1.objectNumber), this.grid[i][j].objectNumber)) {
			return true;
		} else
			return false;
		
	}
	
	public boolean percolates()
	{
		// Check to see if any of the top row components are connected with the bottom row components. If so the systems percolates.
	
		boolean percolate = false;
		if (this.uf.connected((virtualsite1.objectNumber), (virtualsite2.objectNumber))) {
			percolate = true;
		}
		
		return percolate;
	}
	
	private void checkBottom(int i, int j)
	{
		if (this.grid[i+1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i+1][j].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i+1][j].objectNumber);
		}
	}
	private void checkTop(int i, int j)
	{
		if (this.grid[i-1][j].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i-1][j].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i-1][j].objectNumber);
		}
	}
	private void checkLeft(int i, int j)
	{
		if (this.grid[i][j + 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j + 1].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j + 1].objectNumber);
		}
		
	}
	private void checkRight(int i, int j)
	{
		if (this.grid[i][j - 1].isOpen) {
			this.uf.union(this.grid[i][j].objectNumber, this.grid[i][j - 1].objectNumber);
			//StdOut.println(this.grid[i][j].objectNumber + " " + this.grid[i][j - 1].objectNumber);
		}
	}
	
	
    private Percolation performPercolation()
	{
		// N is the size of the grid N x N
				//int N = 20;
				int Max = this.N-1;
				int Min = 0;

				// Instantiate a new Percolation grid
				Percolation objectGrid = new Percolation(this.N);
				//Keep track of how many sites have been open

				//Instantiate a new QuickFind Object
				
				
				// While the grid does not Percolate I keep opening squares and check for adjacent squares that are open
				while (!objectGrid.percolates())
				{
					// First I open a random square in the grid
					int i = Min + (int)(Math.random() * ((Max - Min) + 1));
					int j = Min + (int)(Math.random() * ((Max - Min) + 1));
					
					if (objectGrid.grid[i][j].isOpen != true) {
						objectGrid.open(i, j);
					}
				}
				
				//StdOut.println("Number of open sites: " + objectGrid.siteOpenCount);
				
				return objectGrid;
	}
    
	
	public static void main(String[] args) {
	/*	
		int N= 20;
		
		Percolation objectGrid = new Percolation(N);
		objectGrid = objectGrid.performPercolation();
		
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
		
		
		int N= 20;
		
		Percolation objectGrid = new Percolation(N);
		objectGrid.open(1, 5);
		objectGrid.open(2, 5);
		objectGrid.open(3, 5);
		objectGrid.open(4, 5);
		objectGrid.open(5, 5);
		StdOut.println(objectGrid.isFull(5,5));
		*/
	}
	
}