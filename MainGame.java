import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;
import java.util.Scanner;
import java.io.*;
public class MainGame extends JPanel {
	private static JFrame frame;
	private static JPanel bigPanel;
	private static JLayeredPane layeredPane;
	private static boolean debug;
	private static int frameSize;
	private static int spotSize;
	private static int numSpots;
	private static int topBarSize;
	private static double bombPercent;
	private static HashMap<JButton, Integer> rowNumbers;
	private static HashMap<JButton, Boolean> bombList;
	private static HashMap<JButton, Boolean> markedTiles;
	private static ArrayList<ArrayList<JButton>> buttons;
	private static ArrayList<Color> colors;
	private static int time;
	private static Timer timer;
	private static int tilesRemaining;
	private static int bombsRemaining;
	private static int score;
	private static String bombText;
	public MainGame(int numSpots, int spotSize){
		this.numSpots=numSpots;
		this.spotSize=spotSize;
		runGame();
	}
	public MainGame(int numSpots){
		this.numSpots=numSpots;
		spotSize=30;
		runGame();
	}
	public MainGame(){
		numSpots=20;
		spotSize=30;
		runGame();
	}
	
	public static void main(String[] args){
		numSpots=20;
		spotSize=30;
		runGame();
	}

	private static void runGame(){
		//set to true to mark all bombs
		debug=false;
		frameSize=numSpots*spotSize+30;
		topBarSize=44;

		bombPercent=0.12;
		//bombPercent=.04;

		//initialize rows
		rowNumbers = new HashMap<JButton, Integer>();

		//initialize marked tiles
		markedTiles = new HashMap<JButton, Boolean>();

		//initial bomb value
		bombsRemaining=(int)(numSpots*numSpots*bombPercent);

		//initial time value
		time=0;

		bombText="✦";

		//setting colors for number tiles
		colors = new ArrayList<Color>();
		colors.add(new Color(66, 134, 244));
		colors.add(new Color(75, 242, 89));
		colors.add(new Color(255, 89, 89));
		colors.add(new Color(188, 89, 255));
		colors.add(new Color(193, 0, 0));
		
		tilesRemaining=numSpots*numSpots;

		//create frame for minesweeper
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(frameSize,frameSize+topBarSize+4); //top bar is 22 tall
		frame.setTitle("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

		//create layered pane
		layeredPane =  new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(frameSize,frameSize));

		//create large panel
		bigPanel = new JPanel();
		bigPanel.setLayout(new BorderLayout());
		bigPanel.setBounds(0,20,frameSize,frameSize);

		//create bomblist
		bombList = new HashMap<JButton, Boolean>();

		//time
		JLabel timeLabel = new JLabel("Time: "+time);
		timeLabel.setSize(350,30);
		timeLabel.setVerticalTextPosition(JLabel.CENTER);
		timeLabel.setHorizontalTextPosition(JLabel.CENTER);
		timeLabel.setBounds(20,0,350,15);
		bigPanel.add(timeLabel);

		//instruction
		JLabel instructionLabel = new JLabel("Use right click to mark bombs");
		instructionLabel.setSize(350,30);
		instructionLabel.setVerticalTextPosition(JLabel.CENTER);
		instructionLabel.setHorizontalTextPosition(JLabel.CENTER);
		instructionLabel.setBounds(frameSize-205,0,350,15);
		bigPanel.add(instructionLabel);

		//quit panel
		JPanel quitPanel = new JPanel();
		quitPanel.setLayout(new BorderLayout());
		quitPanel.setBounds(frameSize/2-75/2,frameSize+5,75,25);

		MenuSelectionButton quitButton = new MenuSelectionButton("Quit");
		quitPanel.add(quitButton);
		frame.add(quitPanel);

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				restartGame();
			}
		});

		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time++;
				timeLabel.setText("Time: "+time);
			}
		});

		JPanel tilePanel = new JPanel();
		tilePanel.setLayout(new GridBagLayout());

		//create all tiles
		buttons = createButtons(tilePanel);

		bigPanel.add(tilePanel);
		layeredPane.add(bigPanel,new Integer(1));
		frame.add(layeredPane);
		createBombs();

		//set frame to visible
		frame.setVisible(true);

		//create listeners for all buttons
		for(int i=0;i<buttons.size();i++){
			for(int j=0;j<buttons.get(0).size();j++){
				JButton button = buttons.get(i).get(j);
				button.addActionListener(new ActionListener() {
					//Override
					public void actionPerformed(ActionEvent action){
						if(!timer.isRunning()){
							timer.start();
						}
						if(markedTiles.get(button)==null || markedTiles.get(button)==false){
							if(bombList.get(button)!=null && bombList.get(button)==true){
								button.setText(bombText);
								button.setForeground(Color.RED);
								button.setBackground(Color.RED);
							  	button.setEnabled(false);
							  	button.setOpaque(true);
							  	gameEndLose();
							  	frame.repaint();
							} else {
								int numSurrounding = findAdjacentBombs(button);
								if(numSurrounding!=0){
									button.setText(""+numSurrounding);
									button.setEnabled(false);
									button.setBackground(colors.get(numSurrounding-1));
							  		button.setOpaque(true);
									frame.repaint();
									tilesRemaining--;
									if(checkIfWon()){
										int numBombs=(int)(numSpots*numSpots*bombPercent);
										double percent = (1 - ((double)(tilesRemaining-bombsRemaining)) / ((double)(numSpots*numSpots-numBombs))) * 100;
										if(hasHighScore()){
											gameEndWin(true);
											getLeaderboardName();
										} else {
											gameEndWin(false);
										}
									}
								} else {
									clearZeroSquares(button);
									frame.repaint();
									if(checkIfWon()){
										if(hasHighScore()){
											gameEndWin(true);
											getLeaderboardName();
										} else {
											gameEndWin(false);
										}
									}
								}
							}
						}
					}
				});

				//add right click listener
				button.addMouseListener(new MouseAdapter(){
					public void mouseReleased(MouseEvent e){
						if(SwingUtilities.isRightMouseButton(e) && button.isEnabled()==true){
							if(markedTiles.get(button)!=null){
								boolean isMarked = markedTiles.get(button);
								if(!isMarked){
									button.setText(bombText);
									button.setForeground(Color.RED);
									button.setBackground(Color.RED);
									markedTiles.put(button,true);
								} else {
									button.setText("");
									markedTiles.put(button,false);
								}
							} else {
								markedTiles.put(button,true);
								button.setText(bombText);
								button.setForeground(Color.RED);
								button.setBackground(Color.RED);
								button.setPreferredSize(new Dimension(spotSize,spotSize));
							}
						}
					}
				});
			}
		}
	}

	private static boolean checkIfWon(){
		int numBombs = (int)((numSpots*numSpots)*bombPercent);
		return (tilesRemaining-numBombs==0);
	}

	private static ArrayList<ArrayList<JButton>> createButtons(JPanel frame){
		ArrayList<ArrayList<JButton>> buttons = new ArrayList<ArrayList<JButton>>();
		for(int i=0;i<numSpots;i++){
			buttons.add(new ArrayList<JButton>());
		}

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i=0;i<numSpots;i++){
			for(int j=0;j<numSpots;j++){
				JButton button = new JButton();

				button.setPreferredSize(new Dimension(spotSize,spotSize));
				c.gridx = j;
				c.gridy = i;
				bombList.put(button,false);
				buttons.get(i).add(button);
				rowNumbers.put(button,i);
				frame.add(button, c);
			}
		}
		JButton button = new JButton("");
		button.setBounds(0,0,0,0);
		button.setVisible(false);
		frame.add(button);

		return buttons;
	}

	private static void createBombs(){
		int bombCount=0;
		int maxBombs = (int)(Math.pow(numSpots,2)*bombPercent);
		while(bombCount<maxBombs){
			int randNumX = (int)(Math.random()*(numSpots));
			int randNumY = (int)(Math.random()*(numSpots));
			JButton button = buttons.get(randNumY).get(randNumX);
			if(bombList.get(button)!=true){
				bombList.remove(button);
				bombList.put(button, true);
				bombCount++;
				if(debug){
					button.setText(bombText);
				}
			}
		}
	}

	private static void clearZeroSquares(JButton button){
		int numSurrounding=findAdjacentBombs(button);
		if(bombList.get(button)==false && numSurrounding==0 && button.isEnabled()==true){
			button.setText("");
			button.setEnabled(false);
			button.setBackground(new Color(170,170,170));
			button.setOpaque(true);
			tilesRemaining--;
			ArrayList<JButton> adjacentTiles = getAdjacentTiles(button);
			for(int i=0;i<adjacentTiles.size();i++){
				JButton b = adjacentTiles.get(i);
				clearZeroSquares(b);
			}
		} else if(bombList.get(button)==false && numSurrounding!=0 && button.isEnabled()==true){
			button.setText(numSurrounding+"");
			button.setEnabled(false);
			button.setBackground(colors.get(numSurrounding-1));
			button.setOpaque(true);
			tilesRemaining--;
		}
	}

	private static ArrayList<JButton> getAdjacentTiles(JButton button){
		ArrayList<JButton> toReturn = new ArrayList<JButton>();
		int row = rowNumbers.get(button);
		int index = buttons.get(row).indexOf(button);
		if(row-1>=0){
			ArrayList<JButton> rowList = buttons.get(row-1);
			if(index-1>=0){
				toReturn.add(rowList.get(index-1));
			}
			if(index>=0){
				toReturn.add(rowList.get(index));
			}
			if(index+1<numSpots){
				toReturn.add(rowList.get(index+1));
			}
		}

		if(row+1<numSpots){
			ArrayList<JButton> rowList = buttons.get(row+1);
			if(index-1>=0){
				toReturn.add(rowList.get(index-1));
			}
			if(index>=0){
				toReturn.add(rowList.get(index));
			}
			if(index+1<numSpots){
				toReturn.add(rowList.get(index+1));
			}
		}

		ArrayList<JButton> rowList = buttons.get(row);
		if(index-1>=0){
			JButton tile = rowList.get(index-1);
			toReturn.add(rowList.get(index-1));
		}
		if(index+1<numSpots){
			toReturn.add(rowList.get(index+1));
		}

		return toReturn;
	}

	private static int findAdjacentBombs(JButton button){
		int row = rowNumbers.get(button);
		int index = buttons.get(row).indexOf(button);
		int count=0;	
		if(row-1>=0){
			ArrayList<JButton> rowList = buttons.get(row-1);
			if(index-1>=0){
				JButton tile = rowList.get(index-1);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
			if(index>=0){
				JButton tile = rowList.get(index);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
			if(index+1<numSpots){
				JButton tile = rowList.get(index+1);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
		}
		
		if(row+1<numSpots){
			ArrayList<JButton> rowList = buttons.get(row+1);
			if(index-1>=0){
				JButton tile = rowList.get(index-1);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
			if(index>=0){
				JButton tile = rowList.get(index);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
			if(index+1<numSpots){
				JButton tile = rowList.get(index+1);
				if(bombList.get(tile)!=null && bombList.get(tile)==true){
					count++;
				}
			}
		}
		
		ArrayList<JButton> rowList = buttons.get(row);
		if(index-1>=0){
			JButton tile = rowList.get(index-1);
			if(bombList.get(tile)!=null && bombList.get(tile)==true){
				count++;
			}
		}
		if(index+1<numSpots){
			JButton tile = rowList.get(index+1);
			if(bombList.get(tile)!=null && bombList.get(tile)==true){
				count++;
			}
		}
		
		return count;
	}

	private static void gameEndLose(){
		timer.stop();
		for(JButton button : bombList.keySet()){
			button.setEnabled(false);
		}
		JPanel losePanel = new JPanel();
		losePanel.setLayout(null);
		losePanel.setBounds(frameSize/2-250/2,frameSize/2-125/2,250,125);
		Color boxColor = new Color(90,90,90,200);
		losePanel.setBackground(boxColor);

		boxColor=new Color(90,90,90,0);

		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(250,35));
		textPanel.setBounds(0,0,250,35);
		textPanel.setLayout(new BorderLayout());
		textPanel.setBackground(boxColor);

		DrawText textOne = new DrawText(250,4,26,"Game Over");
		textPanel.add(textOne);
		losePanel.add(textPanel);

		JPanel textPanel2 = new JPanel();
		textPanel2.setPreferredSize(new Dimension(250,50));
		textPanel2.setBounds(0,35,250,50);
		textPanel2.setLayout(new BorderLayout());
		textPanel2.setBackground(boxColor);

		int numBombs=(int)(numSpots*numSpots*bombPercent);
		int percent = (int)((1 - ((double)(tilesRemaining-bombsRemaining)) / ((double)(numSpots*numSpots-numBombs))) * 100);

		DrawText textTwo = new DrawText(250,10,20,"Time: "+time+" seconds  "+percent+"% complete");
		textPanel2.add(textTwo);
		losePanel.add(textPanel2);

		MenuSelectionButton restartButton = new MenuSelectionButton("Restart");
		int panelWidth = 250;
		int panelHeight = losePanel.getSize().height;
		int buttonWidth=250/2;
		int buttonHeight=panelHeight/4;
		restartButton.setBounds(panelWidth/2-buttonWidth/2,panelHeight-buttonHeight-10,buttonWidth,buttonHeight);
		restartButton.setBackground(boxColor);

		losePanel.add(restartButton);

		layeredPane.add(losePanel, new Integer(2));

		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				restartGame();
			}
		});
	}

	private static void gameEndWin(boolean hasHighScore){
		timer.stop();
		for(JButton button : bombList.keySet()){
			if(bombList.get(button)==true){
				button.setEnabled(false);
			}
		}
		
		if(!hasHighScore){
			JPanel winPanel = new JPanel();
			winPanel.setLayout(null);
			winPanel.setBounds(frameSize/2-250/2,frameSize/2-125/2,250,125);
			Color boxColor = new Color(90,90,90,200);
			winPanel.setBackground(boxColor);

			boxColor=new Color(90,90,90,0);

			JPanel textPanel = new JPanel();
			textPanel.setPreferredSize(new Dimension(250,35));
			textPanel.setBounds(0,0,250,35);
			textPanel.setLayout(new BorderLayout());
			textPanel.setBackground(boxColor);

			DrawText textOne = new DrawText(250,0,24,"Winner!");
			textPanel.add(textOne);
			winPanel.add(textPanel);

			JPanel textPanel2 = new JPanel();
			textPanel2.setPreferredSize(new Dimension(250,50));
			textPanel2.setBounds(0,30,250,50);
			textPanel2.setLayout(new BorderLayout());
			textPanel2.setBackground(boxColor);

			DrawText textTwo = new DrawText(250,2,20,"Time: "+time+" seconds  "+"Score: "+score);
			textPanel2.add(textTwo);
			winPanel.add(textPanel2);

			MenuSelectionButton restartButton = new MenuSelectionButton("Restart");
			int panelWidth = 250;
			int panelHeight = winPanel.getSize().height;
			int buttonWidth=250/2;
			int buttonHeight=panelHeight/4;
			restartButton.setBounds(panelWidth/2-buttonWidth/2,panelHeight-buttonHeight-10,buttonWidth,buttonHeight);
			restartButton.setBackground(boxColor);

			winPanel.add(restartButton);

			layeredPane.add(winPanel, new Integer(2));

			restartButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent action){
					restartGame();
				}
			});
		}
	}

	public static void restartGame(){
		JFrame oldFrame = frame;
		MineSweeper newGame = new MineSweeper();
		oldFrame.dispose();
	}

	private static void updateLeaderboard(String playerName){
		File file = new File("leaderboard.txt");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> scores = new ArrayList<Integer>();
		boolean hasHighScore = false;

		try {
			Scanner scan = new Scanner(file);
			int i=0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] strArr = line.split(";");
				names.add(strArr[0]);
				scores.add(Integer.parseInt(strArr[1]));
				i++;
			}
		} catch (Exception e){
			System.out.println(e);
		}

		//add new high score (if it exists) to names/scores
		int scoreSize=scores.size();
		if(scoreSize==0){
			scores.add(score);
			names.add(playerName);
		} else {
			boolean inserted = false;
			for(int i=0;i<scoreSize;i++){
				if(score>scores.get(i)){
					scores.add(i,score);
					names.add(i,playerName);
					inserted=true;
					break;
				}
			}
			if(!inserted){
				scores.add(scoreSize,score);
				names.add(scoreSize,playerName);
			}
		}
		gameEndWin(hasHighScore);
		
		//rewrite leaderboard file
		scoreSize=scores.size();
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.close();
			FileWriter writer = new FileWriter("leaderboard.txt");
			BufferedWriter out = new BufferedWriter(writer);
			for(int i=0;i<Math.min(scoreSize,5);i++){
				String toWrite = names.get(i)+";"+scores.get(i);
				out.write(toWrite);
				out.newLine();
			}
			out.close();
		} catch (Exception e){
			System.out.println(e);
		}
	
	}

	private static boolean hasHighScore(){
		File file = new File("leaderboard.txt");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> scores = new ArrayList<Integer>();
		boolean hasHighScore = false;

		//SCORE CALCULATION
		score = (int)(Math.pow(numSpots,6)/(time)/150);

		try {
			Scanner scan = new Scanner(file);
			int i=0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] strArr = line.split(";");
				names.add(strArr[0]);
				scores.add(Integer.parseInt(strArr[1]));
				i++;
			}
		} catch (Exception e){
			System.out.println(e);
		}

		int scoreSize=scores.size();
		if(scoreSize<5){
			hasHighScore=true;
		} else {
			for(int i=0;i<scoreSize;i++){
				if(score>scores.get(i)){
					hasHighScore=true;
					break;
				}
			}
		}

		return hasHighScore;
	}

	private static void getLeaderboardName(){
		JPanel namePanel = new JPanel();
		namePanel.setLayout(null);
		namePanel.setBounds(frameSize/2-250/2,frameSize/2-150/2,250,150);
		Color boxColor = new Color(90,90,90,200);
		namePanel.setBackground(boxColor);

		boxColor=new Color(90,90,90,0);

		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(250,75));
		textPanel.setBounds(0,0,250,75);
		textPanel.setLayout(new BorderLayout());
		textPanel.setBackground(boxColor);

		DrawHighScore lineOne = new DrawHighScore(250,150,24,"New High Score!  "+score);
		textPanel.add(lineOne);

		JPanel nameFieldPanel = new JPanel();
		nameFieldPanel.setPreferredSize(new Dimension(75,25));
		nameFieldPanel.setBounds(250/2-75/2,150/2+10,75,25);
		nameFieldPanel.setLayout(new BorderLayout());
		nameFieldPanel.setBackground(new Color(0,0,0));
		
		JTextField nameField = new JTextField("Name",10);
		nameFieldPanel.add(nameField);

		JPanel continueButtonPanel = new JPanel();
		continueButtonPanel.setLayout(new BorderLayout());
		continueButtonPanel.setPreferredSize(new Dimension(150,35));
		continueButtonPanel.setBounds(250/2-150/2,150-35,150,35);
		continueButtonPanel.setBackground(boxColor);

		MenuSelectionButton continueButton = new MenuSelectionButton("Continue");
		continueButtonPanel.add(continueButton);

		namePanel.add(continueButtonPanel);
		namePanel.add(nameFieldPanel);
		namePanel.add(textPanel);

		layeredPane.add(namePanel, new Integer(2));
		frame.repaint();

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				String text = nameField.getText();
				updateLeaderboard(text);
				restartGame();
			}
		});
	}
}