package client_p.ui_p;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SeatChangePop extends JDialog {

	public SeatChangePop() {
		setBounds(760, 440, 400, 200);
		getContentPane().setLayout(null);
		getContentPane().setBackground(MyColor.jinBeige);
		
		JLabel msg = new JLabel("이동할 좌석을 클릭해 주세요");
		msg.setFont(new Font("굴림", Font.PLAIN, 25));
		msg.setBounds(0, 0, 380, 80);
		getContentPane().add(msg);
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton okBtn = new JButton("확인");
		okBtn.setBackground(MyColor.jinBeige2);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}});
		okBtn.setBounds(120, 90, 150, 50);
		getContentPane().add(okBtn);
		setUndecorated(true);
		setModal(true);
		setVisible(true);
	}
}