import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class BoardVisualizerMainMethod extends JComponent{
	public static void main (String[] args){

	  JFrame frame = new JFrame();
	  JLabel label=new JLabel("Welcome to the game! Red moves first.",SwingConstants.CENTER);
	  label.setVerticalAlignment(SwingConstants.TOP);
      int width = 510;
      int height = 510;
      frame.setSize(width, height);
      frame.setTitle("Checkers");
      BoardGraphic boardGraph=new BoardGraphic();
      frame.add(boardGraph);
      frame.setVisible(true);
      frame.add(label);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Board board=boardGraph.board();
      JLabel skip=new JLabel("SKIP TURN");
	  skip.setVerticalAlignment(SwingConstants.BOTTOM);
	  skip.setHorizontalAlignment(SwingConstants.LEFT);
	  JLabel doubleJ=new JLabel("DOUBLE JUMP");
	  doubleJ.setVerticalAlignment(SwingConstants.BOTTOM);
	  doubleJ.setHorizontalAlignment(SwingConstants.RIGHT);



      class MyMouseListener implements MouseListener{
      	private boolean alreadyClicked;
      	private boolean redTurn;
      	private BoardSpace from;
      	private BoardSpace to;
      	private boolean crownedBefore;
      	private int numTimesMoved;
      	private boolean doubleJump;

      	public MyMouseListener(){
      		alreadyClicked=false;
      		redTurn=(board.getTurn().equals("Red")) ? true: false;;
      		from=null;
      		to=null;
      		crownedBefore=false;
      		doubleJump=false;
      	}

      	public void mouseClicked(MouseEvent event){
      		if(event.getX()<55||event.getX()>455||event.getY()<35||event.getY()>435){
      			if(event.getX()<80&&event.getY()>470){
      				//you are in the skip button area
      				redTurn=!redTurn;
      				String turn= (redTurn) ? "Red" : "Black";
      				label.setText("Turn Skipped. "+turn+"'s turn");
      			}else if(doubleJump&&(event.getX()>400&&event.getY()>470)){
      				//they want to exit double jump mode
      				doubleJump=false;
      				frame.getContentPane().setBackground(Color.WHITE);
      				doubleJ.setText("DOUBLE JUMP");
      				label.setText("Try a move that isn't a double jump");
      			}else if(event.getX()>400&&event.getY()>470){
      				label.setText("Double jump mode: ON");
      				//you are in the double jump area
      				doubleJump=true;
      				frame.getContentPane().setBackground(Color.RED);
      				doubleJ.setText("I CHANGED MY MIND");
      			}else{
      				System.out.println("Outside board");
      			}
      		}else if(doubleJump){
      			System.out.println("DOUBLE JUMP");
      			if(!alreadyClicked){
      				if(numTimesMoved>0){
      					//you are on the second jump
      					from=board.getBoardSpace(event.getX(),event.getY());
      					System.out.println("FROM:"+from+"Coords:("+from.getXCoord()+","+from.getYCoord()+")");
      					System.out.println("TO 1:"+to+"Coords:("+to.getXCoord()+","+to.getYCoord()+")");
      					String turnBegin= (redTurn) ? "Red" : "Black";
      					Checker moved=from.getChecker();
      					alreadyClicked=true;
      					if(!(from.getXCoord()==to.getXCoord()&&from.getYCoord()==to.getYCoord())){
      						label.setText("You must perform both jumps with the same checker. Try Again!");
      						alreadyClicked=false;
      					}else if(!from.isOccupied()){
      						label.setText("Please click a space with occupied by a "+turnBegin+ " checker to start your turn");
      						alreadyClicked=false;
      					}else if((from.getChecker().isRed()&&!redTurn)||(from.getChecker().isBlack()&&redTurn)){
      						label.setText("It is not your turn! Please move a "+turnBegin+" piece.");
      						alreadyClicked=false;
      					}else if(moved.isCrowned()){
      						crownedBefore=true;
      					}
      					numTimesMoved++;
      				}else{
	      					from= board.getBoardSpace(event.getX(),event.getY());
	      					System.out.println("FROM:"+from+"Coords:("+from.getXCoord()+","+from.getYCoord()+")");
	      					alreadyClicked=true;
	      					String turnBegin= (redTurn) ? "Red" : "Black";
	      					Checker moved=from.getChecker();
	      					if(!from.isOccupied()){
	      						label.setText("Please click a space with occupied by a "+turnBegin+ " checker to start your turn");
	      						alreadyClicked=false;
	      					}else if((from.getChecker().isRed()&&!redTurn)||(from.getChecker().isBlack()&&redTurn)){
	      						label.setText("It is not your turn! Please move a "+turnBegin+" piece.");
	      						alreadyClicked=false;
	      					}else if(moved.isCrowned()){
	      						crownedBefore=true;
	      					}
	      					numTimesMoved++;
      					}
      			}else if(alreadyClicked){
      				to= board.getBoardSpace(event.getX(),event.getY());
      				System.out.println("TO 2:"+to+"Coords:("+to.getXCoord()+","+to.getYCoord()+")");
      				try{
      					board.move(from,to);
      					board.update();
      					boardGraph.repaint();
      					String turn= (redTurn) ? "Red" : "Black";
      					if(!crownedBefore&&to.getChecker().isCrowned()){
      						JOptionPane.showMessageDialog(frame,turn+"'s checker is crowned and can now move forwards and backwards!","Crowning",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Crown.jpg"));

      					}
      					if(board.gameOver()){
      						int playAgain=JOptionPane.showConfirmDialog(frame,turn+ " lost the game! Play Again?", "Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon("WinMessage.jpg"));
      						if(playAgain==JOptionPane.YES_OPTION){
      							board.reset();
      							boardGraph.repaint();
      							label.setText("Welcome to the game! Red moves first.");
      						}else if(playAgain==JOptionPane.NO_OPTION){
      							frame.setVisible(false); //you can't see me!
								frame.dispose();
      						}
      					}else if(numTimesMoved>1){
      						redTurn=!redTurn;
      						turn= (redTurn) ? "Red" : "Black";
      						label.setText(turn+ "'s turn!");
      						alreadyClicked=false;
      						crownedBefore=false;
      						numTimesMoved=0;
      						doubleJump=false;
      						frame.getContentPane().setBackground(Color.WHITE);
      					}else{
      						label.setText(turn+ " has jumped once and can try for another");
      						alreadyClicked=false;
      						crownedBefore=false;
      					}
      				}catch(IllegalArgumentException e){
      					label.setText("This move is invalid! Please try again");
      					alreadyClicked=false;
      					crownedBefore=false;
      					numTimesMoved--;
      				}
      			}
      		}else if(!alreadyClicked){
      				from= board.getBoardSpace(event.getX(),event.getY());
      				alreadyClicked=true;
      				String turnBegin= (redTurn) ? "Red" : "Black";
      				Checker moved=from.getChecker();
      				if(!from.isOccupied()){
      					label.setText("Please click a space with occupied by a "+turnBegin+ " checker to start your turn");
      					alreadyClicked=false;
      				}else if((from.getChecker().isRed()&&!redTurn)||(from.getChecker().isBlack()&&redTurn)){
      					label.setText("It is not your turn! Please move a "+turnBegin+" piece.");
      					alreadyClicked=false;
      				}else if(moved.isCrowned()){
      					crownedBefore=true;
      				}
      		}else if(alreadyClicked){
      			to= board.getBoardSpace(event.getX(),event.getY());
      			try{
      				board.move(from,to);
      				board.update();
      				boardGraph.repaint();
      				String turn= (redTurn) ? "Red" : "Black";
      				if(!crownedBefore&&to.getChecker().isCrowned()){
      					JOptionPane.showMessageDialog(frame,turn+"'s checker is crowned and can now move forwards and backwards!","Crowning",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Crown.jpg"));

      				}
      				redTurn=!redTurn;
      				turn= (redTurn) ? "Red" : "Black";

      				if(board.gameOver()){
      					int playAgain=JOptionPane.showConfirmDialog(frame,turn+ " lost the game! Play Again?", "Game Over",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("WinMessage.jpg"));
      					if(playAgain==JOptionPane.YES_OPTION){
      						board.reset();
      						boardGraph.repaint();
      						label.setText("Welcome to the game! Red moves first.");
      					}else if(playAgain==JOptionPane.NO_OPTION){
      						frame.setVisible(false); //you can't see me!
							frame.dispose();
      					}
      				}else{
      					label.setText(turn+ "'s turn!");
      					alreadyClicked=false;
      					crownedBefore=false;
      				}
      			}catch(IllegalArgumentException e){
      				label.setText("This move is invalid! Please try again");
      				alreadyClicked=false;
      				crownedBefore=false;
      			}
      		}
      	}
      	public void mouseReleased(MouseEvent event){
      	}
      	public void mousePressed(MouseEvent event){
      	}
      	public void mouseEntered(MouseEvent event){
      	}
      	public void mouseExited(MouseEvent event){
      	}
    }

    MyMouseListener listener = new MyMouseListener();
    boardGraph.addMouseListener(listener);

	  frame.add(skip);
      frame.setVisible(true);
      frame.add(doubleJ);
      frame.setVisible(true);
    frame.setVisible(true);
    String information="Welcome to the game of checkers!";
    information=information+"\n*To move a checker click the checker you want\nand then click the space to move it to";
    information=information+"\n*Remember that pieces move diagonally along light spaces and can only move forward\nuntil they have reached the opposite side of the board and been crowned";
    information=information+"\n*To double jump a checker first click\n the double jump button and then perform 3 actions";
    information=information+"\n\t1. Click the checker you want to move";
    information=information+"\n\t2. Click the middle space where you will finish your first jump";
    information=information+"\n\t3. Once the first stolen piece has disappeared, click the next spot you would like to jump to";
    information=information+"\n*** NB: If you enter Double jump mode and change your mind,\n you must click 'I CHANGE MY MIND' before you move any pieces to return to normal mode";
    String[] choices={"Let's play!"};
    JOptionPane.showOptionDialog(null, information, "Rules and Instructions",
        JOptionPane.PLAIN_MESSAGE, 0, new ImageIcon("checkerIcon.png"), choices, null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

 }
}