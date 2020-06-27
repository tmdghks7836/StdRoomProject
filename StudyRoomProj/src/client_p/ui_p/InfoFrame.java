package client_p.ui_p;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import data_p.product_p.room_p.RoomProduct;

public class InfoFrame extends JFrame {

	JTextArea textArea;
	String id = BaseFrame.getInstance().userData.id;

	public static void main(String[] args) {
		InfoFrame frame = new InfoFrame();
	}

	public InfoFrame() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		String date = "";

		setBounds(100, 100, 550, 650);
		getContentPane().setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(45, 133, 449, 349);
		getContentPane().add(scrollPane_1);

		textArea = new JTextArea();
		String text = "";
		ArrayList<RoomProduct> roomList = BaseFrame.getInstance().userData.myReservationList;
		if (roomList.size() != 0) {
			for (RoomProduct data : roomList) {

				for (Calendar cal : data.calendarList) {
					date = sdf.format(cal.getTime());
					text += "이용자 ID : " + id + "\n구매한 좌석/룸 명 : " + data.name + " / " + "금액 : "
							+ data.price + "원" + "\n구매한 시간 : " + date + "\n";
				}
			}
		} else {
			text = "이용내역이없습니다.";
		}
		textArea.setText(text);
		scrollPane_1.setViewportView(textArea);

		JLabel mainLabel = new JLabel("<html>잡아 스터디룸<br>이용 내역<html>");
		mainLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setBounds(17, 15, 494, 103);
		getContentPane().add(mainLabel);

		JButton okButton = new JButton("확인");
		okButton.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		okButton.setBounds(158, 514, 186, 65);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(okButton);
		setVisible(true);

	}
}