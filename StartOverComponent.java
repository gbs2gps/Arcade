import javax.swing.*;
	import java.awt.*;
	import java.awt.geom.*;

	public class StartOverComponent extends JComponent {
	   private int x;
	   private int y;
	   private GridComponent g;

	   public StartOverComponent(GridComponent g){
	      this.x = 600;
	      this.y = 50;
	      Grid newGrid = g.getGrid().sameGrid(g.getGrid());
	      this.g = new GridComponent(newGrid);

	   }

	   public void newG(GridComponent g){
	      Grid newGrid = g.getGrid().sameGrid(g.getGrid());
	      this.g = new GridComponent(newGrid);
	   }

	   public GridComponent newGrid(){
	      return g;
	   }
	   
	   public void paintComponent(Graphics g) {
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setColor(Color.GRAY);
	      g2.fillRect(x,y,100,50);
	      g2.setColor(Color.BLACK);
	      g2.drawString("START OVER", x+12, y+30);
	   }

}

