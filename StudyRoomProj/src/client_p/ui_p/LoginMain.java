package client_p.ui_p;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import client_p.ClientNet;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsLoginSyn;
import packetBase_p.ELoginType;
import packetBase_p.EResult;
import packetBase_p.PacketBase;
import server_p.packet_p.ack_p.ScLoginAck;

public class LoginMain extends JPanel implements Receivable, MouseListener {

	JTextField idTextF;
	JPasswordField passwordField;
	CheckRoomInfo chkroominfo;
	JCheckBox changeBox;
	JLabel idLabel,logLabel;
	JButton logInBtn;
	JDialog logDialog;
	String info = "등록한 ID 또는 휴대폰 번호를 입력하세요";
	String idInfo = "등록한 ID를 입력하세요";
	String phinfo = "등록한 휴대폰 번호를 입력하세요";
	int cnt = 0;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 900, 1000);
		frame.getContentPane().add(new LoginMain());
		frame.setVisible(true);
	}

	public LoginMain() {
		// 서버에서 받은 로그인 응답 클래스와 그에 맞는 함수클래스 연결
//		PacketMap.getInstance().map.put(ScLoginAck.class, new ReceiveLoginAck());
		setLayout(null);

		JLabel lblNewLabel = new JLabel("로그인창");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lblNewLabel.setBounds(230, 21, 469, 110);
		add(lblNewLabel);

		idLabel = new JLabel("ID");
		idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		idLabel.setBounds(150, 156, 100, 55);
		add(idLabel);

		idTextF = new JTextField();//ID 입력필드
		idTextF.setText(info);
		idTextF.setToolTipText("");
		idTextF.setFont(new Font("맑은 고딕", Font.ITALIC, 14));
		idTextF.setBounds(300, 156, 328, 55);
		add(idTextF);
		idTextF.setColumns(10);
		idTextF.addMouseListener(this);

		changeBox = new JCheckBox("휴대폰 번호로 로그인하기");//휴대폰번호로 로그인 전환 버튼
		changeBox.setBounds(300, 220, 300, 70);
		changeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (changeBox.isSelected() == true) {
					idLabel.setText("Phone");//눌렀을때 폰번호로
					idTextF.setText(phinfo);
				} else {
					idLabel.setText("ID");// 다시 눌렀을 때 아이디로
					idTextF.setText(idInfo);
				}

			}
		});
		add(changeBox);

		JLabel lblPw = new JLabel("PW");
		lblPw.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		lblPw.setBounds(150, 300, 51, 54);
		add(lblPw);

		passwordField = new JPasswordField();//비밀번호 입력 필드
		passwordField.setBounds(300, 300, 328, 54);
		add(passwordField);

		logInBtn = new JButton("로그인");//로그인 버튼
		logInBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		logInBtn.setBounds(300, 400, 120, 45);
		add(logInBtn);
		logInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CsLoginSyn packet = new CsLoginSyn(idTextF.getText(), passwordField.getText(), !changeBox.isSelected());
				ClientNet.getInstance().sendPacket(packet);
			}
		});

		JButton signUpBt = new JButton("회원가입");//회원가입 버튼
		signUpBt.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		signUpBt.setBounds(480, 400, 120, 45);
		add(signUpBt);
		signUpBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaseFrame.getInstance().signUpFrame.setVisible(true);
				BaseFrame.getInstance().signUpFrame.textFieldSet();

			}
		});
	}

	@Override
	public void receive(PacketBase packet) {//로그인 응답 받는 부분

		ScLoginAck ack = (ScLoginAck) packet;
		BaseFrame.getInstance().userData = ack.userdata;
		if (ack.eResult == EResult.SUCCESS) {//로그인 성공시
			
			if (BaseFrame.getInstance().loginType == ELoginType.KIOSK) {

				BaseFrame.getInstance().openMainLayout(ack.roomList, null, null, ack.lockerList);

				chkroominfo = new CheckRoomInfo();
				chkroominfo.start();
				
			} else if (BaseFrame.getInstance().loginType == ELoginType.MOBILE) {
				BaseFrame.getInstance().updateData(ack.roomList, null, null, ack.lockerList);
				
				ReservationInfoMain rif = new ReservationInfoMain();
				
			}
		}else if(ack.eResult == EResult.NOT_FOUND_DATA) {//로그인 실패시
			//로그인시 ID 또는 비밀번호 미입력 했을 때 띄워주는 창
			logTextChk();
			cnt++;
			if(cnt==3) {
				logLabel.setText("<html>미입력 로그인 3회 진행으로 30초 뒤에 "
						+ "<br>다시 실행 해주시기 바랍니다.<html>");
				logChk log = new logChk();
				log.start();
				cnt = 0;
			}
		}
	}
	
	class logChk extends Thread{
		@Override
		public void run() {//쓰레드 30초 줘서 로그인 버튼 비활성화
			try {
				for (int i = 30; i >= 0 ; i--) {
					logInBtn.setEnabled(false);
				}
				sleep(30000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			logInBtn.setEnabled(true);
		}
	}
	
	public void logSet() {//작업진행 후 로그인 화면 왔을 때 텍스트 set
		idTextF.setText(info);
		passwordField.setText("");
	}
	
	public void logTextChk() {//ack 가 Not Found Data 일 때 띄워주는 창
		logDialog = new JDialog();
		logDialog.setBounds(250, 250, 300, 300);
		logDialog.setLayout(new GridLayout(2,1));
		logLabel = new JLabel("등록한 ID와 비밀번호를 입력하세요");
		logDialog.add(logLabel);
		JButton logChkButton = new JButton("확인");
		logDialog.add(logChkButton);
		logChkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logDialog.dispose();
				logSet();
				
			}
		});
		logDialog.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		idTextF = (JTextField) e.getSource();
		String idfield = idTextF.getText();

		if (idfield.equals(info) || idfield.equals(idInfo) || idfield.equals(phinfo)) {
			idTextF.setText("");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}