import javax.swing.*;
import java.awt.*;
public class DrawText extends JComponent{
	private String text;
	private int x,y,size;
	public DrawText(int x, int y, int size, String text){
		this.x=x;
		this.y=y;
		this.size=size;
		this.text=text;
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2.setColor(Color.WHITE);
		Font font = new Font("Gill Sans", Font.PLAIN, size);
        g2.setFont(font);

        String[] strings = text.split("  ");
        FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();

        if(strings.length>1){
        	for(int i=0;i<strings.length;i++){
        		text=strings[i];
        		int adv = metrics.stringWidth(text);
				g2.drawString(text, x/2-adv/2,hgt-y+i*20);
        	}
        } else {
        	int adv = metrics.stringWidth(text);
			g2.drawString(text, x/2-adv/2,hgt-y);
        }
		
		g2.dispose();
	}

	public String getText(){
		return text;
	}

}