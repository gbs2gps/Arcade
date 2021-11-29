import javax.swing.*;
import java.awt.*;
public class DrawLeaderboard extends JComponent{
	String[] names;
	int[] scores;
	int frameWidth;
	int frameHeight;
	public DrawLeaderboard(String[] names, int[] scores, int frameWidth, int frameHeight){
		this.names=names;
		this.scores=scores;
		this.frameWidth=frameWidth;
		this.frameHeight=frameHeight;
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		Color color1 = Color.WHITE;
        Color color2 = new Color(90,90,90);
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight()*2, color2);
		g2.setPaint(gp);
		g2.fillRect(0,0,frameWidth,frameHeight);

		String titleText = "Leaderboard";
		g2.setColor(Color.BLACK);

		Font font = new Font("Gill Sans", Font.PLAIN, 30);
        g2.setFont(font);

		FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(titleText);
		g2.drawString(titleText, frameWidth/2-adv/2, hgt);

		for(int i=0;i<names.length;i++){
			if(names[i]!=null && !names[i].isEmpty()){
				String scoreText = names[i]+" - "+scores[i];
				font = new Font("Gill Sans", Font.PLAIN, 18);
        		g2.setFont(font);
        		g2.setColor(Color.BLACK);
        		metrics = g2.getFontMetrics(font);
				hgt = metrics.getHeight();
				adv = metrics.stringWidth(scoreText);
				g2.drawString(scoreText, frameWidth/2-adv/2, 60+hgt*i);
			}
		}
	}
}