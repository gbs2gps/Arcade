import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
public class MineSweeper{
	private static int topBarSize=22;
	private static int frameWidth=400;
	private static int frameHeight=180;
	private static Color color = new Color(160,160,160);
	private static MainGame player1;
	private static JFrame menu;
	private static JPanel menuPanel;
	private static JPanel easyButtonPanel;
	private static JPanel mediumButtonPanel;
	private static JPanel hardButtonPanel;
	private static JPanel leaderboardButtonPanel;
	private static JPanel returnButtonPanel;
	private static int buttonWidth, buttonHeight;
	public MineSweeper(){
		runMinesweeper();
	}
	public static void go(){
		runMinesweeper();
	}

	private static void runMinesweeper(){
		menu = new JFrame();
		menu.setResizable(false);
		menu.setSize(frameWidth,frameHeight+topBarSize); //top bar is 22 pixels tall
		menu.setTitle("Minesweeper");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		menu.setLocation(dim.width/2-menu.getSize().width/2, dim.height/2-menu.getSize().height/2);

		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBounds(0,0,frameWidth,frameHeight);

		MinesweeperSelection drawMenu = new MinesweeperSelection(0,0,frameWidth,frameHeight,color);
		menuPanel.add(drawMenu);

		buttonWidth=75;
		buttonHeight=30;

		easyButtonPanel = new JPanel();
		easyButtonPanel.setBackground(color);
		easyButtonPanel.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		easyButtonPanel.setLayout(new BorderLayout());
		easyButtonPanel.setBounds(frameWidth/2-buttonWidth/2,frameHeight/2-45,buttonWidth,buttonHeight);

		MenuSelectionButton easyButton = new MenuSelectionButton("Easy");
		easyButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		easyButtonPanel.add(easyButton);

		mediumButtonPanel = new JPanel();
		mediumButtonPanel.setBackground(color);
		mediumButtonPanel.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		mediumButtonPanel.setLayout(new BorderLayout());
		mediumButtonPanel.setBounds(frameWidth/2-buttonWidth/2,frameHeight/2-10,buttonWidth,buttonHeight);

		MenuSelectionButton mediumButton = new MenuSelectionButton("Medium");
		mediumButtonPanel.add(mediumButton);

		hardButtonPanel = new JPanel();
		hardButtonPanel.setBackground(color);
		hardButtonPanel.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		hardButtonPanel.setLayout(new BorderLayout());
		hardButtonPanel.setBounds(frameWidth/2-buttonWidth/2,frameHeight/2+25,buttonWidth,buttonHeight);

		MenuSelectionButton hardButton = new MenuSelectionButton("Hard");
		hardButtonPanel.add(hardButton);

		leaderboardButtonPanel = new JPanel();
		leaderboardButtonPanel.setBackground(color);
		leaderboardButtonPanel.setPreferredSize(new Dimension(120,buttonHeight));
		leaderboardButtonPanel.setLayout(new BorderLayout());
		leaderboardButtonPanel.setBounds(frameWidth/2-120/2,frameHeight/2+buttonHeight*2,120,buttonHeight);

		MenuSelectionButton leaderboardButton = new MenuSelectionButton("Leaderboard");
		leaderboardButtonPanel.add(leaderboardButton);

		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				menu.dispose();
				player1 = new MainGame(10,30);
			}
		});

		mediumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				menu.dispose();
				player1 = new MainGame(15,30);
			}
		});

		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				menu.dispose();
				player1 = new MainGame(25,30);
			}
		});

		leaderboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				showLeaderboard();
			}
		});

		menu.add(easyButtonPanel);
		menu.add(mediumButtonPanel);
		menu.add(hardButtonPanel);
		menu.add(leaderboardButtonPanel);
		menu.add(menuPanel);
		menu.setVisible(true);
	}

	public static void showLeaderboard(){
		menu.getContentPane().removeAll();

		String[] names = new String[5];
		int[] scores = new int[5];

		File file = new File("leaderboard.txt");
		try {
			Scanner scan = new Scanner(file);
			int i=0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] strArr = line.split(";");
				names[i]=strArr[0];
				if(Integer.parseInt(strArr[1])!=0){
					scores[i]=Integer.parseInt(strArr[1]);
				}
				i++;
			}
		} catch (Exception e){
			System.out.println(e);
		}

		DrawLeaderboard ldbd = new DrawLeaderboard(names, scores, frameWidth, frameHeight);
		ldbd.setBounds(0,0,frameWidth,frameHeight);

		returnButtonPanel = new JPanel();
		returnButtonPanel.setBackground(new Color(0,0,0,0));
		returnButtonPanel.setBounds(frameWidth/2-120/2,frameHeight-31,120,34);

		MenuSelectionButton returnButton = new MenuSelectionButton("Return");
		returnButton.setPreferredSize(new Dimension(100,30));
		returnButtonPanel.add(returnButton);

		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBounds(0,0,frameWidth,frameHeight);

		menuPanel.add(returnButtonPanel);
		menuPanel.add(ldbd);
		menu.add(menuPanel);
		menu.repaint();
		menu.setVisible(true);

		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action){
				//menu.getContentPane().removeAll();
				menu.dispose();
				runMinesweeper();
			}
		});
	}

}

	