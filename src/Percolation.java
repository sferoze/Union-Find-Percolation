public class Percolation {
	
	boolean isOpen;
	int objectNumber;
	Percolation[][] grid;
	QuickFindUF uf;
	
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// N is the size of the grid N x N
		int N = 10;
		int Max = N-1;
		int Min = 0;
		
		// Instantiate a new Percolation grid
		Percolation objectGrid = new Percolation(N);
		//Instantiate a new QuickFind Object
		objectGrid.uf = new QuickFindUF((N * N));

		// While the grid does not Percolate I keep opening squares and check for adjacent squares that are open
		while (!objectGrid.percolates())
		{
			// First I open a random square in the grid
			int i = Min + (int)(Math.random() * ((Max - Min) + 1));
			int j = Min + (int)(Math.random() * ((Max - Min) + 1));
			objectGrid.grid[i][j].isOpen = true;
			
			/*  After the random square is open, I check the squares above, below, left and right, to the current open one.
			 	If any of those squares are open as well, I call the union command to join the components
			 	If a square on the boundary of the grid is open, I can only check the inner squares or else I get array
			 	out of bounds error, so that is why my first four if statements check if i or j is at 0, or 9. 
			 	
			 	So if two adjacent squares are open I call union and print out which components where joined in union. 
			*/
			
			if (i ==0) {
				if (objectGrid.grid[i+1][j].isOpen) {
					objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i+1][j].objectNumber);
					StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i+1][j].objectNumber);
				}
			} else if (i == 9) {
				if(objectGrid.grid[i-1][j].isOpen) {
					objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i-1][j].objectNumber);
					StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i-1][j].objectNumber);
				}
			} else if (j ==0) {
				if (objectGrid.grid[i][j + 1].isOpen) {
					objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i][j + 1].objectNumber);
					StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i][j + 1].objectNumber);
				}
			} else if ( j==9) {
				if (objectGrid.grid[i][j - 1].isOpen) {
					objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i][j - 1].objectNumber);
					StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i][j -1].objectNumber);
				}
			}
			else if (objectGrid.grid[i+1][j].isOpen)
			{
				objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i+1][j].objectNumber);
				StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i+1][j].objectNumber);
			} else if(objectGrid.grid[i-1][j].isOpen) {
				objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i-1][j].objectNumber);
				StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i-1][j].objectNumber);
			} else if (objectGrid.grid[i][j + 1].isOpen) {
				objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i][j + 1].objectNumber);
				StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i][j + 1].objectNumber);
			} else if (objectGrid.grid[i][j - 1].isOpen) {
				objectGrid.uf.union(objectGrid.grid[i][j].objectNumber, objectGrid.grid[i][j - 1].objectNumber);
				StdOut.println(objectGrid.grid[i][j].objectNumber + " " + objectGrid.grid[i][j -1].objectNumber);
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