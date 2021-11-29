import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class SizeComponent extends JComponent {
   private int x;
   private int y;

   public SizeComponent(){
      this.x = 50;
      this.y = 5;
   }
   
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.DARK_GRAY);
      g2.fillRect(x,y,100,40);
      g2.setColor(Color.WHITE);
            g2.drawString("10", x+12, y+20);
      g2.setColor(Color.GRAY);
      g2.fillRect(x+100,y,100,40);
                  g2.setColor(Color.WHITE);
            g2.drawString("25", x+112, y+20);
      g2.setColor(Color.DARK_GRAY);
                  
      g2.fillRect(x+200,y,100,40);
      g2.setColor(Color.WHITE);

            g2.drawString("50", x+212, y+20);
      g2.setColor(Color.GRAY);
      g2.fillRect(x+300,y,100,40); 
            g2.setColor(Color.WHITE);

                  g2.drawString("100", x+312, y+20);
                        g2.setColor(Color.BLACK);

                  g2.drawString("← PICK YOUR DESIRED DIMENSIONS", x+412, y+20);


   }
   }