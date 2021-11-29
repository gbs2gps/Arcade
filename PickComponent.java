import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;



public class PickComponent extends JComponent {
   private int x;
   private int y;

   public PickComponent(){
      this.x = 5;
      this.y = 5;
   }
   
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.GRAY);
      g2.fillRect(x,y,40,40);
      g2.setColor(Color.WHITE);
      g2.drawString("1", x+10, y+20);
      g2.setColor(Color.DARK_GRAY);
      g2.fillRect(x,y+40,40,40);
                  g2.setColor(Color.WHITE);
            g2.drawString("2", x+10, y+60);
      g2.setColor(Color.GRAY);
                  
      g2.fillRect(x,y+80,40,40);
      g2.setColor(Color.WHITE);

            g2.drawString("3", x+10, y+100);
      g2.setColor(Color.DARK_GRAY);
      g2.fillRect(x,y+120,40,40); 
            g2.setColor(Color.WHITE);

                  g2.drawString("4", x+10, y+140);
                  g2.setColor(Color.GRAY);
      g2.fillRect(x,y+160,40,40); 
            g2.setColor(Color.WHITE);
                              g2.drawString("5", x+10, y+180);

            g2.setColor(Color.DARK_GRAY);
      g2.fillRect(x,y+200,40,40); 
            g2.setColor(Color.WHITE);
                              g2.drawString("6", x+10, y+220);

            g2.setColor(Color.GRAY);
      g2.fillRect(x,y+240,40,40); 
            g2.setColor(Color.WHITE);
                              g2.drawString("7", x+10, y+260);

            g2.setColor(Color.DARK_GRAY);
      g2.fillRect(x,y+280,40,40); 
            g2.setColor(Color.WHITE);
                              g2.drawString("8", x+10, y+300);

            g2.setColor(Color.GRAY);
      g2.fillRect(x,y+320,40,40); 
            g2.setColor(Color.WHITE);
                              g2.drawString("9", x+10, y+340);

                                          g2.setColor(Color.BLACK);
                                          g2.drawString("↑", x+15, y+380);
                                          g2.drawString("pick", x+7, y+400);
                                          g2.drawString("how", x+6, y+420);
                                          g2.drawString("many", x+3, y+440);
                                          g2.drawString("colors", x, y+460);
                                          g2.drawString("you", x+7, y+480);
                                          g2.drawString("want", x+4, y+500);





   }
   }