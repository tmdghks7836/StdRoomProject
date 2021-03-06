package client_p.ui_p;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JLabel;

public class SetNowTime extends Thread 
{
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
	JLabel lb;
	String current_day;

	public SetNowTime(JLabel lb) {
		this.lb = lb;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				lb.setText(date.format((Calendar.getInstance().getTime())));//현재시간 변경
			} 
			catch (InterruptedException e1){
				e1.printStackTrace();
			}
		}
	}
}