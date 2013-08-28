import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Sites extends JFrame {
	
	private Percolation objectGrid;
	int ncount;

	Sites(String name, Percolation obj, int n)
	{
        super(name);
        setSize(800,800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.objectGrid = obj;
        this.ncount = n;
	}
	
public void paint(Graphics g) {       
      
		super.paint(g);
		g.setColor(Color.gray);
    	g.fillRect(0,0, 800, 800);
    	
    	
    	int x = 100;
    	int y = 100;
    	
	
        for (int i=0; i < ncount; i++){
			for (int j =0; j < ncount; j++) {
				if (objectGrid.grid[i][j].isOpen) {
					g.setColor(Color.blue);
					g.fillRect(x, y, 20, 20);
					x += 21;
				}
				else {
					g.setColor(Color.black);
					g.fillRect(x, y, 20, 20);
					x += 21;
				}
			}
			x = 100;
			y += 21;
		}
       
    }
}

