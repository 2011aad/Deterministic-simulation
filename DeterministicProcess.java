import java.util.LinkedList;
import java.util.Queue;

public class DeterministicProcess {
	Queue<Line> Lines = new LinkedList<Line>();
	static final double workload = 1;
	static final int inter_arr_time = 12;
	static final int stat_tran_time[]= {3,4};
	static final int service_time[]= {6,9};
	
	static int maxWidth,maxHeight,minWidth,minHeight;
	
	static int total_time;
	static int len = 0;
	static boolean busy=true;
	private double rho;
	private int period = stat_tran_time[0]+stat_tran_time[1];
	
	private int time_slot;
	
	public DeterministicProcess(int border,int Width,int Height,double difference) {
		double finish_part = 0;
		double old_finish_part = 0;
		double systime = 0;
		int sys_stat = 1;
		int arri_time = 0;
		double tran_time;
		double departure_time = 0;
		double total_service_time = 0;
		int y_old,y_new;
		
		tran_time = difference - ((int)difference/period)*period;
		init(border,Width,Height);
		
		while(systime<total_time){
			if(busy){
				if((finish_part + (tran_time - systime)/((sys_stat==0)?service_time[0]:service_time[1]))<workload){
					finish_part += (tran_time - systime)/((sys_stat==0)?service_time[0]:service_time[1]);
					//add a line
					y_old = (int)(old_finish_part*0.8*(maxHeight-minHeight)+0.2*(maxHeight-minHeight));
					y_new = (int)(finish_part*0.8*(maxHeight-minHeight)+0.2*(maxHeight-minHeight));
					Lines.add(new Line((int)(systime*time_slot)+border, (int)(tran_time*time_slot)+border, 
							y_old+minHeight, y_new+minHeight,sys_stat));
					sys_stat = 1 - sys_stat;
					systime = tran_time;
					tran_time += ((sys_stat==0)?stat_tran_time[0]:stat_tran_time[1]);
					old_finish_part = finish_part;
				}
				
				else{
					departure_time = systime + (workload - finish_part)*((sys_stat==0)?service_time[0]:service_time[1]);
					//add a line
					y_new = (int)(finish_part*0.8*(maxHeight-minHeight)+0.2*(maxHeight-minHeight));
					Lines.add(new Line((int)(systime*time_slot)+border, (int)(departure_time*time_slot)+border, 
							y_new+minHeight, maxHeight,sys_stat));
					systime = departure_time;
					Lines.add(new Line((int)(systime*time_slot)+border, (int)(tran_time*time_slot)+border, 
							maxHeight, maxHeight,sys_stat));
					total_service_time += (departure_time - arri_time); 
					busy = false;
				}
			}
				
			else{
				arri_time += inter_arr_time;
				while(tran_time<arri_time){					
					sys_stat = 1 - sys_stat;
					systime = tran_time;
					tran_time += ((sys_stat==0)?stat_tran_time[0]:stat_tran_time[1]);	
					Lines.add(new Line((int)(systime*time_slot)+border, (int)(tran_time*time_slot)+border, maxHeight, maxHeight,sys_stat));
				}
				
				systime = (int)arri_time;
				busy = true;
				finish_part = 0;
				old_finish_part = 0;
				Lines.add(new Line((int)(systime*time_slot)+border, (int)(systime*time_slot)+border,minHeight+(int)(0.2*(maxHeight-minHeight)),maxHeight,2));
			}
		}
		rho = total_service_time/total_time;
	}

	public void init(int border,int Width,int Height) {
		total_time = (stat_tran_time[0] + stat_tran_time[1]) * inter_arr_time;
		maxWidth = Width-border;
		maxHeight = Height-border;
		minWidth = border;	
		minHeight = border;
		time_slot = (maxWidth-minWidth)/total_time;
		for(int i=0;i<total_time;i++)
			Lines.add(new Line(i*time_slot+minWidth, i*time_slot+minWidth,maxHeight,maxHeight-5, 2));
	}

	public Queue<Line> getData(){
		Queue<Line> L = new LinkedList<Line>();
		while(Lines.size()>0)
			L.add(Lines.remove());
		
		return L;
	}

	public double getrho() {
		return this.rho;
	}
	
	public int getPeriod() {
		return this.period;
	}

}
