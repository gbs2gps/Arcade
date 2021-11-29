import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.geom.*;
import java.io.IOException;
import java.io.File;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;



public class ArcadeViewer {
	   public static void main(String[] args){
	   	      JFrame frame = new JFrame();
	   	      int width = 2000;
      		  int height = 2000;
      		  
      		  frame.setTitle("Welcome to the JAGS ARCADE!!!! Click the game that will bring you the most joy!");

              JLabel image = new JLabel();
      		 try {
      			image.setIcon(new ImageIcon(ImageIO.read(new File("Arcade.png"))));

      		  } catch (IOException e){
      		  	e.printStackTrace();
      		  }
      		  frame.pack();
      		//  frame.setVisible(true);
      		 
              frame.add(image);
              frame.setSize(width, height);
      		 // frame.setVisible(true);
      		  

      		  class MyMouseListener implements MouseListener {
         		public void mouseExited(MouseEvent e) {}
         		public void mouseEntered(MouseEvent e) {}
         		public void mousePressed(MouseEvent e) {

                  int x = e.getX();
                  int y = e.getY();
                  System.out.println("getX"+x);
                  System.out.println("y"+y);
                  if((x>360&&x<500)&&(y>330&&y<540)){
                     GameViewer.go();
                  } else if((x>130&&x<340)&&(y>300&&y<485)){
                     BoardVisualizer.go();

                  } else if((x>1100&&x<1300)&&(y>180&&y<600)){
                     MineSweeper john=new MineSweeper();

                  }
               }          
         		public void mouseReleased(MouseEvent e) {}
         		public void mouseClicked(MouseEvent e) {
         			
         		}
         			
         	  }
      
      
      
      MyMouseListener listener = new MyMouseListener();
      image.addMouseListener(listener);
      image.setFocusable(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      
     
   }
}