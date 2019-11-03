import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Prueba extends JPanel implements Runnable{

	private final int WINDOW_WIDTH,
			  		  WINDOW_HEIGHT,
			  		  DELAY;
	
	private Thread animator;
	
	private int cellSize,
				cols,
				rows;
	
	private int[][] grid;
	
	public Prueba() {
		super();
		
		this.WINDOW_WIDTH = 1077;
		this.WINDOW_HEIGHT = 720;
		this.DELAY = 1000;
		
		this.cellSize = 10;
		this.cols = this.WINDOW_WIDTH/this.cellSize;
		this.rows = this.WINDOW_HEIGHT/this.cellSize;
		
		this.grid = new int[this.cols][this.rows];
		
		this.grid[70][35] = 1;
		
		this.animator = new Thread(this);
	    this.animator.start();
	    
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.WHITE);
		this.pintaGrid(g);
		this.paintVirus(g);
	}
	
	private void pintaGrid(Graphics g) {
		g.setColor(new Color(230, 230, 230));
		for (int i = 0; i < this.cols; i++) {
			g.drawLine(i*this.cellSize, 0, i*this.cellSize, this.WINDOW_HEIGHT);
		}
		
		for (int i = 0; i < this.rows; i++) {
			g.drawLine(0, i*this.cellSize, this.WINDOW_WIDTH, i*this.cellSize);
		}
	}
	
	public void paintVirus(Graphics g) {
		int x, y;
		g.setColor(new Color(0, 0, 0));
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				if(grid[i][j] == 1) {
					x = this.cellSize*i;
					y = this.cellSize*j;
					g.fillRect(x, y, this.cellSize, this.cellSize);
				} 
			}
		}
	}
	
	public void updateGrid() {
		LinkedList<int[]> cellsToChange = new LinkedList<>();
		for(int i=1; i<this.cols-1; i++) {
    		for(int j=1; j<this.rows-1; j++) {
    			if(grid[i+1][j] == 1 || grid[i-1][j] == 1 || grid[i][j+1] == 1 || grid[i][j-1] == 1) {
    				int[] toAdd = {i,j}; 
    				cellsToChange.add(toAdd);
    			}
    		}
    	}

		for (int[] i : cellsToChange) {
			int x = i[0];
			int y = i[1];
			grid[x][y] = 1;
		}
	}
	

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (true) {

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            
            try {
                Thread.sleep(sleep);
                this.updateGrid();
                this.repaint();
            } catch (InterruptedException e) {
            
            }
            beforeTime = System.currentTimeMillis();
            
        }
    }
	
	public static void main(String[] args) {
		
		Prueba p = new Prueba();
		
		JFrame jf = new JFrame();
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(p.WINDOW_WIDTH, p.WINDOW_HEIGHT);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.add(p);

	}

}
