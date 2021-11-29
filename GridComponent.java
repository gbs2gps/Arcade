import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GridComponent extends JComponent {
   
   private int x;
   private int y;
   public Grid grid;
   private int size;
   private int maxPoints;
   private int currPoints;
   private int xScore;
   private int yScore;
   private GridComponent algorithm;
   private int numColors;
   private int xThing;
   private int yThing;
  

   
   public GridComponent(Grid grid){
      this.x = 0;
      this.y = 0;
      this.grid = grid;
      this.size = grid.getSize();
      maxPoints = (int) (grid.getSize()*(1+.15*grid.getNumColors()));
      currPoints = 0;
      this.xScore = 600;
      this.yScore = 150;
      this.numColors = grid.getNumColors();
      this.xThing = 50;
      this.yThing = 600;
      
   }

   public Grid gridReturn(GridComponent gridComponent){
    Grid g = gridComponent.getGrid();
    grid = g;
    return grid;
   }
   
   public Grid getGrid(){
    return grid;
   }


   public Grid changeSizeGrid(int size){
      this.size = size;
      grid.changeSize(size);
      maxPoints = (int) (grid.getSize()*(1+.1*grid.getNumColors()));
      currPoints = 0;
      return grid;
   }

   public Grid changeNumColorsGrid(int numColors){
      this.numColors = numColors;
      grid.changeNumColors(numColors);
      maxPoints = (int) (grid.getSize()*(1+.1*grid.getNumColors()));
      currPoints = 0;
      return grid;
   }

   public void addPoint(){
      currPoints += 1;
   }

   public void backToZero(){
      currPoints = 0;
   }


   public void paintComponent(Graphics g) {
         Graphics2D g2 = (Graphics2D) g;
         int stuffX = 50;
         int stuffY = 50;
         int toAdd = 500/size;
         MyNode other = grid.getStartNode();
            for(int i = 0; i < size; i++){
               MyNode current = other;
               for(int j = 0; j < size; j++){
                  if(current.toString().equals("black")){
                              g2.setColor(Color.BLACK);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("green")){
                              g2.setColor(Color.GREEN);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("red")){
                              g2.setColor(Color.RED);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("blue")){
                              g2.setColor(Color.BLUE);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("magenta")){
                              g2.setColor(Color.MAGENTA);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("cyan")){
                              g2.setColor(Color.CYAN);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("yellow")){
                              g2.setColor(Color.YELLOW);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("gray")){
                              g2.setColor(Color.GRAY);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("orange")){
                              g2.setColor(Color.ORANGE);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } else if(current.toString().equals("pink")){
                              g2.setColor(Color.WHITE);
                              g2.fillRect(stuffX+x,stuffY+y,toAdd,toAdd);
                  } 
               stuffX += toAdd;
               current = current.getNext();
               }
               stuffX=50;
               stuffY+=toAdd;
               other = other.getDown();
            }

      g2.setColor(Color.GRAY);
      g2.fillRect(xScore,yScore, 100,50);
      g2.setColor(Color.BLACK);
      g2.drawString(""+ maxPoints, xScore+12, yScore+40);
      g2.drawString("PAR:", xScore+12, yScore+20);
      g2.setColor(Color.GRAY);
      g2.fillRect(xScore,yScore+100,100,50);
      g2.setColor(Color.BLACK);
      g2.drawString("POINTS:", xScore+12, yScore+120);
      g2.drawString(""+ currPoints, xScore+12, yScore+140);

         int thingX = 0;
         int add = 500/numColors;
         if(numColors == 1){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


         } else if(numColors == 2){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


         } else if(numColors == 3){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 4){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 5){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.CYAN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 6){
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.CYAN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.YELLOW);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 7){ 
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.CYAN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.YELLOW);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GRAY);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 8){ 
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.CYAN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.YELLOW);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GRAY);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.ORANGE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           } else if(numColors == 9){ 
           g2.setColor(Color.RED);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GREEN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.BLUE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.MAGENTA);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.CYAN);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.YELLOW);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.GRAY);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.ORANGE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           thingX += add;
           g2.setColor(Color.WHITE);
                      g2.fillRect(thingX+xThing,yThing,add,50);


           }
           g2.setColor(Color.BLACK);

           g2.drawString("Pick the color you would like to turn to black... remember the goal of the",65, 670);
           g2.drawString("game is to turn every sqaure black in the least number of moves. Have fun!",60, 685);


   }
  
   
   public Grid changeColor(String color){

      addPoint();
      
      while(isNotDone(color)){
      MyNode other = grid.getStartNode();

      for(int i = 0; i < grid.getSize(); i++){
        MyNode stuff = other;
        for(int j = 0; j < grid.getSize(); j++){
        if(stuff.getNext()!=null){
          if(stuff.getData().equals(color)&&stuff.getNext().getData().equals("black")){
            stuff.setData("black");
          } 
        }
        if(stuff.getUp()!=null){
          if(stuff.getData().equals(color)&&stuff.getUp().getData().equals("black")){
            stuff.setData("black");
          } 
        }
        if(stuff.getDown()!=null){
          if(stuff.getData().equals(color)&&stuff.getDown().getData().equals("black")){
            stuff.setData("black");
          } 
        }
        if(stuff.getPrev()!=null){
          if(stuff.getData().equals(color)&&stuff.getPrev().getData().equals("black")){
            stuff.setData("black");
          }
        }

          stuff = stuff.getNext();
        }
          other = other.getDown();
      }
    }

      return grid;
   }

   public boolean isNotDone(String color){
    boolean flag = false;
          MyNode other = grid.getStartNode();

    for(int i = 0; i < grid.getSize(); i++){
        MyNode stuff = other;
        for(int j = 0; j < grid.getSize(); j++){
        if(stuff.getNext()!=null){
          if(stuff.getData().equals(color)&&stuff.getNext().getData().equals("black")){
            flag = true;
            } 
        }
        if(stuff.getUp()!=null){
          if(stuff.getData().equals(color)&&stuff.getUp().getData().equals("black")){
            flag = true;
          } 
        }
        if(stuff.getDown()!=null){
          if(stuff.getData().equals(color)&&stuff.getDown().getData().equals("black")){
            flag = true;
          } 
        }
        if(stuff.getPrev()!=null){
          if(stuff.getData().equals(color)&&stuff.getPrev().getData().equals("black")){
            flag = true;
          }
        }

          stuff = stuff.getNext();
        }
          other = other.getDown();
      }

      return flag;
   }

   public int getMaxPoints(){
      return maxPoints;
   }

   public int getCurrPoints(){
      return currPoints;
   }

   public int getNumColors(){
    return numColors;
   }

   public boolean isOneColor(){
   boolean toReturn = true;
   MyNode other = grid.getStartNode();
   for(int i = 0; i < grid.getSize(); i++){
      MyNode current = other;
      for(int j = 0; j < grid.getSize(); j++){
         if(!current.toString().equals("black")){
            toReturn = false; 
         }
         current = current.getNext();
      }
   other = other.getDown();
   }

   return toReturn;
}

public String firstLine(){
  return grid.firstLine();
}
public String toString(){
   return grid.toString();
}

}



     