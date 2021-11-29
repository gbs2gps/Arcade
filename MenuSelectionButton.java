import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
public class MenuSelectionButton extends JButton{
	public MenuSelectionButton(String text){
		super(text);
		setBorder(new LineBorder(Color.BLACK, 2, false));
		setFocusPainted(true);
		setVisible(true);
	}

	@Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g.create();

        Font font = new Font("Gill Sans", Font.PLAIN, 18);
        g2.setFont(font);

		FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(getText());
        //g2.setPaint(new GradientPaint(new Point(0, 0),Color.WHITE));
        Color color1 = Color.WHITE;
        Color color2 = new Color(90,90,90);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight()*2, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        g2.setPaint(Color.BLACK);
        int height = (int)(getHeight()*0.7);
        g2.drawString(getText(), getWidth()/2-adv/2, height);
        g2.dispose();

        //super.paintComponent(g);
    }
}