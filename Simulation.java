
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Simulation extends JFrame{
	private static int border = 100;
	private static int Width = 1400;
	private static int Height = 400;
	private static double diff = 0;
	private static final long serialVersionUID = 1L;
	
	private JSlider adjustDiff;
	
	public Simulation(final Plot_Graph g,int period) {
		this.add(g);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Deterministic Process");
		this.setSize(Width,Height);
		this.setResizable(false);
		this.adjustDiff = new JSlider(0, 10*period, 0);
		
		adjustDiff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				diff = (double)(((JSlider)e.getSource()).getValue())/10;
				DeterministicProcess p = new DeterministicProcess(border,Width,Height,diff);
				g.setData(p.getData(),p.getrho());
			}
		});
		this.add(BorderLayout.SOUTH,adjustDiff);
	}
	
	public static void main(String [] args) {
		DeterministicProcess p = new DeterministicProcess(border,Width,Height,diff);
		Plot_Graph g = new Plot_Graph(border,Width,Height);
		g.setData(p.getData(),p.getrho());
		new Simulation(g,p.getPeriod());
	}
}
