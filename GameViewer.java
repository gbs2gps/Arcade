import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GameViewer {
   public static void go(){
      JFrame frame = new JFrame();
      int rando = (int) Math.random()*100;
      int randoSize = 0;
         if(rando<17){
            randoSize = 10;
         } else if(rando<38){
            randoSize = 25;
         } else if(rando<75){
            randoSize = 50;
         } else{
            randoSize = 100;
         }
      int width = 725;
      int height = 950;
      frame.setSize(width, height);
      frame.setTitle("Tap Colors");
      Grid c = new Grid(randoSize, 4);
      c.createGrid();
      c.sameGrid(c);
      GridComponent component = new GridComponent(c);
      System.out.println(component.toString());
      frame.add(component);
      frame.setVisible(true);
      StartOverComponent over = new StartOverComponent(component);
      frame.add(over);
      frame.setVisible(true);
      SizeComponent sizes = new SizeComponent();
      frame.add(sizes);
      frame.setVisible(true);
      PickComponent pick = new PickComponent();
      frame.add(pick);
      frame.setVisible(true);


      
      class MyMouseListener implements MouseListener {
         public void mouseExited(MouseEvent e) {}
         public void mouseEntered(MouseEvent e) {}
         public void mousePressed(MouseEvent e) {} 
         
         public void mouseReleased(MouseEvent e) {}
         public void mouseClicked(MouseEvent e) {
            String[] color = new String[9];
            color[0] = "red";
            color[1] = "green";
            color[2] = "blue";
            color[3] = "magenta";
            color[4] = "cyan";
            color[5] = "yellow";
            color[6] = "gray";
            color[7] = "orange";
            color[8] = "pink";
            int x = e.getX();
            int y = e.getY();
            int stuffX = 50;
            int toAdd = 500/component.getNumColors();
            for(int j = 0; j<component.getNumColors(); j++){
               if((stuffX<=x)&&(x<=(stuffX+toAdd))&&(600<=y)&&(y<=650)){
                  component.changeColor(color[j]);
                  component.repaint();
                  System.out.println(component.firstLine());
                  System.out.println(over.newGrid().firstLine());
                  frame.setVisible(true);
                  

               }
               frame.setVisible(true);
               stuffX+=toAdd;
            }
            int[] numColors = new int[9];
            numColors[0] = 1;
            numColors[1] = 2;
            numColors[2] = 3;
            numColors[3] = 4;
            numColors[4] = 5;
            numColors[5] = 6;
            numColors[6] = 7;
            numColors[7] = 8;
            numColors[8] = 9;
            int k = 5;
            for(int l = 0; l<9; l++){
               if((5<=x)&&(x<=(45))&&(k<=y)&&(y<=k+40)){
                  component.changeNumColorsGrid(numColors[l]);
                  over.newG(component);


                  over.repaint();
                     GridComponent newGrid = over.newGrid();
                     component.gridReturn(newGrid);
                     component.backToZero();
                     component.repaint();
                     over.newG(component);
                     over.repaint();
                  component.repaint();
                  System.out.println(component.firstLine());

                  
                  frame.setVisible(true);
                  
               }
               k+=40;
               frame.setVisible(true);

            }

            int[] numSizes = new int[4];
            numSizes[0] = 10;
            numSizes[1] = 25;
            numSizes[2] = 50;
            numSizes[3] = 100;
            
            int i = 50;
            for(int d = 0; d<4; d++){
               if((i<=x)&&(x<=(i+100))&&(5<=y)&&(y<=45)){
                  component.changeSizeGrid(numSizes[d]);
                  over.newG(component);

                  over.repaint();
                     GridComponent newGrid = over.newGrid();
                     component.gridReturn(newGrid);
                     component.backToZero();
                     component.repaint();
                     over.newG(component);
                     over.repaint();
                  System.out.println(component.firstLine());

                  component.repaint();
                  
                  frame.setVisible(true);
               

               }
               i+=100;
               frame.setVisible(true);

            }

            if((600<=x)&&(x<=(700))&&(50<=y)&&(y<=100)){
               System.out.println("START OVERRR");
               GridComponent newGrid = over.newGrid();
               component.gridReturn(newGrid);
               component.backToZero();
               component.repaint();
               over.newG(component);
               over.repaint();
               System.out.println(component.firstLine());
               frame.setVisible(true);
            }

            if(component.isOneColor()){
               String[] options = {"new grid", "start over with same grid", "go back to arcade"};
               
               int theEnd = JOptionPane.showOptionDialog(null, "CONGRATULATIONS YOU COMPLETED THE GRID WITH A SCORE OF " + component.getCurrPoints()+ " AND A PAR OF " + component.getMaxPoints(), "Pick an option...", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);              
               System.out.println(theEnd);
               if(theEnd == 0){
                  component.changeNumColorsGrid(component.getNumColors());
                  over.newG(component);


                  over.repaint();
                     GridComponent newGrid = over.newGrid();
                     component.gridReturn(newGrid);
                     component.backToZero();
                     component.repaint();
                     over.newG(component);
                     over.repaint();
                  component.repaint();
                  System.out.println(component.firstLine());

                  
                  frame.setVisible(true);

               } if(theEnd==1){
                  GridComponent newGrid = over.newGrid();
               component.gridReturn(newGrid);
               component.backToZero();
               component.repaint();
               over.newG(component);
               over.repaint();
               System.out.println(component.firstLine());
               frame.setVisible(true);
               } if (theEnd == 2){
                                    frame.setVisible(false);

                  frame.dispose();
               }

    }
            


}

}
      
      
      
      MyMouseListener listener = new MyMouseListener();
      component.addMouseListener(listener);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setVisible(true);
      
     
   }
}