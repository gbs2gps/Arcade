import javax.swing.*;
import java.awt.*;
public class MinesweeperSelection extends JComponent{
	private static int x, y, xSize, ySize;
	private static Color color;
	public MinesweeperSelection(int x, int y, int xSize, int ySize, Color color){
		this.x=x;
		this.y=y;
		this.xSize=xSize;
		this.ySize=ySize;
		this.color=color;
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);


		Color color1 = Color.WHITE;
        Color color2 = new Color(90,90,90);
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight()*2, color2);
		g2.setPaint(gp);
		g2.fillRect(x,y,xSize,ySize);

		String titleText = "Minesweeper";
		g2.setColor(Color.BLACK);

		Font font = new Font("Gill Sans", Font.PLAIN, 30);
        g2.setFont(font);

		FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(titleText);
		g2.drawString(titleText, xSize/2-adv/2, hgt);
	}
}