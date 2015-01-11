import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

public class Plot_Graph extends JPanel{
	private static final long serialVersionUID = 1L;
	private int border;
	private int Width,Height;
	private Queue<Line> Lines = new LinkedList<Line>();
	private String rho;
	
	public Plot_Graph(int border,int Width,int Height) {
		this.border = border;
		this.Width = Width;
		this.Height = Height;
		this.setBackground(Color.WHITE);
	}
	
	public void setData(Queue<Line> Lines,double rho){
		while(Lines.size()>0)
			this.Lines.add(Lines.remove());
		this.rho = String.valueOf(rho);
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int maxWidth = Width-border;
		int maxHeight = Height-border;	
		g.setColor(Color.BLACK);
		g.drawLine(border, maxHeight,maxWidth, maxHeight);
		g.drawLine(border, border,border, maxHeight);
		g.drawString("Load="+rho, 50, 50);
		while(Lines.size()>0){
			Line l = Lines.remove();
			switch (l.service_rate) {
				case 0:
					g.setColor(Color.RED);
					break;
				case 1:
					g.setColor(Color.BLUE);
					break;
				case 2:
					g.setColor(Color.BLACK);
					break;
				default:
					break;
			}
			g.drawLine(l.x1, l.y1, l.x2, l.y2);
		}
	}
}
