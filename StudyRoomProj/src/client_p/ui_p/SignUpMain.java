package client_p.ui_p;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import client_p.ClientNet;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsDuplicateIDSyn;
import client_p.packet_p.syn_p.CsSignUpSyn;
import packetBase_p.EResult;
import packetBase_p.PacketBase;
import server_p.packet_p.ack_p.ScDuplicateIDAck;
import server_p.packet_p.ack_p.ScSignUpAck;

public class SignUpMain extends JFrame implements Receivable, MouseListener{

	JTextField nameTextField;
	JTextField idTextField;
	JTextField phoneNumTextField;
	JPasswordField passwordField;
	JPasswordField check_passwordField;
	
	JDialog jd,jd2;
	String text="";
	JLabel label_1,label_2,label_3,label_4,label_5;
	JLabel jl,jl2;
	JButton jb,jb2;
	JButton signUpBtn;
	ArrayList<JTextField> textList = new ArrayList<JTextField>();
	ArrayList<JPasswordField> pTextList = new ArrayList<JPasswordField>();
	
	public SignUpMain(){
		setBounds(100, 100, 900, 1000);
		JPanel mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("회원가입창");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(268, 10, 380, 75);
		mainPane.add(titleLabel);
		
		JLabel idL = new JLabel("아이디");
		idL.setBounds(129, 164, 77, 42);
		mainPane.add(idL);
		
		JLabel nameL = new JLabel("이름");
		nameL.setBounds(129, 112, 77, 42);
		mainPane.add(nameL);
		
		JLabel pwL = new JLabel("비밀번호");
		pwL.setBounds(127, 205, 77, 42);
		mainPane.add(pwL);
		
		JLabel pwChkL = new JLabel("비밀번호확인");
		pwChkL.setBounds(129, 257, 89, 42);
		mainPane.add(pwChkL);
		
		JLabel phonNumL = new JLabel("휴대폰 번호");
		phonNumL.setBounds(129, 309, 77, 42);
		mainPane.add(phonNumL);
		
		nameTextField = new JTextField();//이름
		nameTextField.setBounds(269, 121, 191, 33);
		mainPane.add(nameTextField);
		nameTextField.setColumns(10);
		nameTextField.addMouseListener(this);
		textList.add(nameTextField);
		
		idTextField = new JTextField();//아이디
		idTextField.setColumns(10);
		idTextField.setBounds(269, 168, 191, 33);
		mainPane.add(idTextField);
		idTextField.addMouseListener(this);
		textList.add(idTextField);
		
		phoneNumTextField = new JTextField();//폰번호
		phoneNumTextField.setColumns(10);
		phoneNumTextField.setBounds(271, 318, 191, 33);
		mainPane.add(phoneNumTextField);
		textList.add(phoneNumTextField);
		phoneNumTextField.addMouseListener(this);
		
		signUpBtn = new JButton("회원가입");
		signUpBtn.setBounds(192, 368, 140, 42);
		mainPane.add(signUpBtn);
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				check();
				}});
		
		JButton cancelBtn = new JButton("취소");
		cancelBtn.setBounds(494, 368, 140, 42);
		mainPane.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			
			}});
		
		JButton idChkBtn = new JButton("ID 중복확인");
		idChkBtn.setBounds(477, 169, 105, 33);
		mainPane.add(idChkBtn);
		idChkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CsDuplicateIDSyn packet = new CsDuplicateIDSyn(idTextField.getText());
				ClientNet.getInstance().sendPacket(packet);
			}});
		
		
		passwordField = new JPasswordField();//비밀번호
		passwordField.setBounds(269, 219, 191, 33);
		mainPane.add(passwordField);
		pTextList.add(passwordField);
		passwordField.addMouseListener(this);
		
		check_passwordField = new JPasswordField();//비밀번호 확인
		check_passwordField.setBounds(269, 266, 191, 33);
		mainPane.add(check_passwordField);
		pTextList.add(check_passwordField);
		check_passwordField.addMouseListener(this);
		
		JPanel keybordPane = new JPanel();
		keybordPane.setBounds(12, 431, 860, 300);
		mainPane.add(keybordPane);
		
		label_1 = new JLabel("한글로 입력하세요");
		label_1.setBounds(477, 123, 318, 33);
		mainPane.add(label_1);
		
		label_2 = new JLabel("영문,숫자로 조합된 8자리를 입력하세요");
		label_2.setBounds(477, 216, 318, 33);
		mainPane.add(label_2);
		
		label_3 = new JLabel("입력한 비밀번호와 같게 입력하세요");
		label_3.setBounds(477, 268, 318, 33);
		mainPane.add(label_3);
		
		label_4 = new JLabel("'-'는 제외하고 입력하세요");
		label_4.setBounds(477, 320, 318, 33);
		mainPane.add(label_4);
		
		label_5 = new JLabel("영문,숫자로 조합된 8자리를 입력하세요");
		label_5.setBounds(594, 164, 267, 33);
		mainPane.add(label_5);

		setVisible(false);
		
	}
	

	@Override
	public void receive(PacketBase packet) {
		
		if (packet.getClass() == ScSignUpAck.class) {
			ScSignUpAck ack = (ScSignUpAck) packet;

			if (ack.eResult == EResult.SUCCESS) {
				jd = new JDialog();
				jd.setBounds(50, 50, 150, 150);
				jd.getContentPane().setLayout(new GridLayout(2, 1));
				jl = new JLabel("회원가입 완료");
				jb = new JButton("확인");
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton bbb = (JButton) e.getSource();
						if (bbb.getText().equals("확인")) {
							jd.setVisible(false);
							dispose();
						}
					}
				});

				jd.getContentPane().add(jl);
				jd.getContentPane().add(jb);
				jd.setVisible(true);
			}else if(ack.eResult == EResult.DUPLICATEED_ID) {
				signUpBtn.setEnabled(false);
				jd = new JDialog();
				jd.setBounds(50, 50, 150, 150);
				jd.getContentPane().setLayout(new GridLayout(2, 1));
				jl = new JLabel("ID 중복 확인을 해주세요");
				jb = new JButton("확인");
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton bbb = (JButton) e.getSource();
						if (bbb.getText().equals("확인")) {
							jd.setVisible(false);
							setVisible(true);
							
						}
					}
				});

				jd.getContentPane().add(jl);
				jd.getContentPane().add(jb);
				jd.setVisible(true);
			}
		}else if(packet.getClass() == ScDuplicateIDAck.class) {
			ScDuplicateIDAck ack = (ScDuplicateIDAck) packet;
			
			if(ack.eResult == EResult.SUCCESS) {
				jd2 = new JDialog();
				jd2.setBounds(50, 50, 150, 150);
				jd2.getContentPane().setLayout(new GridLayout(2, 1));
				jl2 = new JLabel("사용 가능한 ID 입니다.");
				jb2 = new JButton("확인");
				jb2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton bbb = (JButton) e.getSource();
						if (bbb.getText().equals("확인")) {
							jd2.setVisible(false);
							setVisible(true);
							signUpBtn.setEnabled(true);
							
						}
					}
				});

				jd2.getContentPane().add(jl2);
				jd2.getContentPane().add(jb2);
				jd2.setVisible(true);
			}else if(ack.eResult == EResult.DUPLICATEED_ID){
				jd2 = new JDialog();
				jd2.setBounds(50, 50, 150, 150);
				jd2.getContentPane().setLayout(new GridLayout(2, 1));
				jl2 = new JLabel("중복된 ID 입니다.");
				jb2 = new JButton("확인");
				jb2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton bbb = (JButton) e.getSource();
						if (bbb.getText().equals("확인")) {
							jd2.setVisible(false);
							setVisible(true);
							signUpBtn.setEnabled(false);
						}
					}
				});

				jd2.getContentPane().add(jl2);
				jd2.getContentPane().add(jb2);
				jd2.setVisible(true);
			}
		}
		
	}

	void check()
	{
		
		String korean = "[가-힣]*";
		String engNum = "[a-zA-Z0-9]*.{7}";
		String passChk = "[a-zA-Z0-9]*.{7}";
		String phoneChk = "010.[0-9]*.{6,7}";
		String name = nameTextField.getText();
		String id = idTextField.getText();
		String pass = passwordField.getText();
		String phoneNum = phoneNumTextField.getText();
		String pass2 = check_passwordField.getText();
		
		try {
			
				if(name.matches(korean)) {
					label_1.setText("입력 확인");
				}else {
					SignUpPop pop = new SignUpPop();
					return;
				}
				if(id.matches(engNum)) {
					label_5.setText("입력 확인");
				}else {
					SignUpPop pop = new SignUpPop();
					return;
				}
				if(pass.matches(passChk)) {
					label_2.setText("입력 확인");
				}else {
					SignUpPop pop = new SignUpPop();
					return;
				}
				if(phoneNum.matches(phoneChk)) {
					label_4.setText("입력 확인");
				}else {
					SignUpPop pop = new SignUpPop();
					return;
				}
				if(pass.matches(pass2)) {
					label_3.setText("입력 확인");
				}else {
					SignUpPop pop = new SignUpPop();
					return;
				}
			
			CsSignUpSyn packet =new CsSignUpSyn(nameTextField.getText(), idTextField.getText(), passwordField.getText(), phoneNumTextField.getText(), "", "");
			ClientNet.getInstance().sendPacket(packet);
		} catch (Exception e) {
			
		}

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		JTextField jtf = (JTextField) e.getSource();

		if (!jtf.getText().equals("")) {
			jtf.setText("");
		}

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}