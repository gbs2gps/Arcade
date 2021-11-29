import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
public class BoardGraphic extends JComponent{
	private Board underlying;

	public BoardGraphic(){
		underlying=new Board();
	}

	public Board board(){
		return underlying;
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		int boxStarterX=55;
		int boxStarterY=35;

		Rectangle outline=new Rectangle(50,30,410,410);
		g2.setColor(Color.BLACK);
		g2.fill(outline);

		for(int i=0;i<8;i++){
		//make a row of boxes
			for(int j=0;j<8;j++){
				Rectangle box=new Rectangle(boxStarterX,boxStarterY,50,50);
				if((i%2==0&&j%2==0)||(i%2==1&&j%2==1)){
					g2.setColor(Color.LIGHT_GRAY);
				}else{
					g2.setColor(Color.BLACK);
				}
				g2.fill(box);
				BoardSpace curr=underlying.getBoardSpace(boxStarterX+10,boxStarterY+10);
				if(curr.isOccupied()){
						Ellipse2D.Double checker = new Ellipse2D.Double(boxStarterX+10,boxStarterY+10,30,30);
						if(curr.getChecker().isRed()){
							g2.setColor(Color.RED);
							g2.fill(checker);
						}else{
							g2.setColor(Color.BLACK);
							g2.fill(checker);
						}
						if(curr.getChecker().isCrowned()){
							Rectangle crown=new Rectangle(boxStarterX+20,boxStarterY+20,10,10);
							g2.setColor(Color.WHITE);
							g2.fill(crown);
						}
				}
				boxStarterX+=50;
			}
			boxStarterY+=50;
			boxStarterX=55;
		}


	}


}