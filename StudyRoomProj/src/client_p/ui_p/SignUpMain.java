package client_p.ui_p;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import client_p.ClientNet;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsDuplicateIDSyn;
import client_p.packet_p.syn_p.CsSignUpSyn;
import packetBase_p.EResult;
import packetBase_p.PacketBase;
import server_p.packet_p.ack_p.ScDuplicateIDAck;
import server_p.packet_p.ack_p.ScSignUpAck;

public class SignUpMain extends JDialog implements Receivable, MouseListener, ActionListener, KeyListener {

	ArrayList<JTextField> textList = new ArrayList<JTextField>();
	ArrayList<JPasswordField> pTextList = new ArrayList<JPasswordField>();

	JTextField nameTextField;
	JTextField idTextField;
	JTextField phoneNumTextField;
	JPasswordField passwordField;
	JPasswordField check_passwordField;

	JLabel jl, jl2, label_1, label_2, label_3, label_4, label_5;
	JButton jb, jb2, jbt;
	JDialog jd, jd2, chkJd, chkSignUp;

	String text = "";

	String korean = "[��-�R]*";
	String eng = ".*[a-zA-Z].*";
	String num = ".*[0-9].*";
	String passChk = "[a-zA-Z0-9].{7}";
	String phoneChk = "010.[0-9].{5,6}";
	String error = "[a-zA-Z0-9]*";

	String name, id, pass, phoneNum, pass2 = null;

	boolean idchk, hpchk;

	public SignUpMain() {
		setBounds(510, 140, 900, 800);

		JPanel mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(null);
		mainPane.setBackground(MyColor.black);
		JLabel titleLabel = new JLabel("ȸ������");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("����", Font.BOLD, 38));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(268, 10, 380, 75);
		mainPane.add(titleLabel);

		JLabel nameL = new JLabel("�̸�");
		nameL.setForeground(Color.WHITE);
		nameL.setFont(new Font("����", Font.BOLD, 20));
		nameL.setHorizontalAlignment(SwingConstants.CENTER);
		nameL.setBounds(30, 161, 139, 75);
		mainPane.add(nameL);

		JLabel idL = new JLabel("���̵�");
		idL.setForeground(Color.WHITE);
		idL.setFont(new Font("����", Font.BOLD, 20));
		idL.setHorizontalAlignment(SwingConstants.CENTER);
		idL.setBounds(30, 248, 139, 66);
		mainPane.add(idL);

		JLabel pwL = new JLabel("��й�ȣ");
		pwL.setForeground(Color.WHITE);
		pwL.setFont(new Font("����", Font.BOLD, 20));
		pwL.setHorizontalAlignment(SwingConstants.CENTER);
		pwL.setBounds(30, 335, 139, 70);
		mainPane.add(pwL);

		JLabel pwChkL = new JLabel("��й�ȣ Ȯ��");
		pwChkL.setForeground(Color.WHITE);
		pwChkL.setFont(new Font("����", Font.BOLD, 20));
		pwChkL.setHorizontalAlignment(SwingConstants.CENTER);
		pwChkL.setBounds(30, 417, 139, 66);
		mainPane.add(pwChkL);

		JLabel phonNumL = new JLabel("�޴��� ��ȣ");
		phonNumL.setForeground(Color.WHITE);
		phonNumL.setFont(new Font("����", Font.BOLD, 20));
		phonNumL.setHorizontalAlignment(SwingConstants.CENTER);
		phonNumL.setBounds(30, 495, 139, 66);
		mainPane.add(phonNumL);

		nameTextField = new JTextField();// �̸�
		nameTextField.setBounds(181, 169, 329, 57);
		mainPane.add(nameTextField);
		nameTextField.setColumns(10);
		nameTextField.addKeyListener(this);
		nameTextField.addMouseListener(this);
		textList.add(nameTextField);

		idTextField = new JTextField();// ���̵�
		idTextField.setColumns(10);
		idTextField.setBounds(181, 253, 329, 57);
		mainPane.add(idTextField);
		idTextField.addMouseListener(this);
		textList.add(idTextField);

		phoneNumTextField = new JTextField();// ����ȣ
		phoneNumTextField.setColumns(10);
		phoneNumTextField.setBounds(181, 503, 329, 57);
		mainPane.add(phoneNumTextField);
		textList.add(phoneNumTextField);
		phoneNumTextField.addMouseListener(this);

		JButton signUpBtn = new JButton("ȸ������");
		signUpBtn.setBackground(MyColor.w_white);
		signUpBtn.setFont(new Font("����", Font.BOLD, 20));
		signUpBtn.setBounds(194, 624, 206, 75);
		mainPane.add(signUpBtn);
		signUpBtn.addActionListener(this);

		JButton idChkBtn = new JButton("ID �ߺ�üũ");
		idChkBtn.setBackground(MyColor.w_white);
		idChkBtn.setBounds(524, 248, 140, 33);
		mainPane.add(idChkBtn);
		idChkBtn.addActionListener(this);

		JButton pwChkBtn = new JButton("�޴��� �ߺ�üũ");
		pwChkBtn.setBackground(MyColor.w_white);
		pwChkBtn.setBounds(524, 512, 140, 33);
		mainPane.add(pwChkBtn);
		pwChkBtn.addActionListener(this);

		JButton cancelBtn = new JButton("���");
		cancelBtn.setBackground(MyColor.w_white);
		cancelBtn.setFont(new Font("����", Font.BOLD, 20));
		cancelBtn.setBounds(496, 624, 206, 75);
		mainPane.add(cancelBtn);
		cancelBtn.addActionListener(this);

		passwordField = new JPasswordField();// ��й�ȣ
		passwordField.setBounds(181, 335, 329, 57);
		mainPane.add(passwordField);
		pTextList.add(passwordField);
		passwordField.addMouseListener(this);

		check_passwordField = new JPasswordField();// ��й�ȣ Ȯ��
		check_passwordField.setBounds(181, 422, 329, 57);
		mainPane.add(check_passwordField);
		pTextList.add(check_passwordField);
		check_passwordField.addMouseListener(this);

		label_1 = new JLabel("�ѱ۷� �Է��ϼ���");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(528, 182, 318, 33);
		mainPane.add(label_1);

		label_2 = new JLabel("����,���ڷ� ���յ� 8�ڸ����Ϸ� �Է��ϼ���");
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(524, 293, 322, 33);
		mainPane.add(label_2);

		label_3 = new JLabel("����,���ڷ� ���յ� 8�ڸ��� �Է��ϼ���");
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(528, 354, 318, 33);
		mainPane.add(label_3);

		label_4 = new JLabel("�Է��� ��й�ȣ�� ���� �Է��ϼ���");
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(528, 434, 318, 33);
		mainPane.add(label_4);

		label_5 = new JLabel("'-'�� �����ϰ� �Է��ϼ���");
		label_5.setForeground(Color.WHITE);
		label_5.setBounds(678, 512, 168, 33);
		mainPane.add(label_5);

		setVisible(false);

		chkJd = new JDialog();
		chkJd.setBackground(MyColor.white);
		chkJd.setBounds(860, 440, 250, 200);
		chkJd.getContentPane().setLayout(new GridLayout(2, 1));
		chkJd.setUndecorated(true);

		JLabel chkLb = new JLabel("��Ȯ�� �������� �Է��ϼ���");
		setLabelForm(chkLb);
		chkJd.getContentPane().add(chkLb);
		JButton chkBt = new JButton("Ȯ��");
		chkBt.setBackground(MyColor.w_white);
		chkJd.getContentPane().add(chkBt);
		chkBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkJd.setVisible(false);
			}
		});
		chkJd.setVisible(false);

		jd2 = new JDialog();// �ߺ�Ȯ��â
		jd2.setBounds(830, 435, 200, 150);
		jd2.getContentPane().setLayout(new GridLayout(2, 1));
		jd2.setUndecorated(true);
		jl2 = new JLabel();
		setLabelForm(jl2);
		jd2.getContentPane().add(jl2);
		jb2 = new JButton("Ȯ��");
		jb2.setBackground(MyColor.w_white);
		jd2.getContentPane().add(jb2);

		chkSignUp = new JDialog();// ȸ������ Ŭ���� ��ȿ�˻� â
		chkSignUp.setBounds(810, 440, 300, 200);
		chkSignUp.getContentPane().setLayout(new GridLayout(2, 1));
		chkSignUp.setUndecorated(true);
		JLabel jll = new JLabel("ID �� �޴��� ��ȣ �ߺ��˻縦 �����ϼ���");
		setLabelForm(jll);
		chkSignUp.getContentPane().add(jll);
		jbt = new JButton("Ȯ��");
		jbt.setBackground(MyColor.w_white);
		chkSignUp.getContentPane().add(jbt);
		chkSignUp.setVisible(false);

		jd = new JDialog();// ȸ������ �Ϸ�â
		jd.setBounds(860, 465, 200, 150);
		jd.getContentPane().setLayout(new GridLayout(2, 1));
		jd.setUndecorated(true);
		jl = new JLabel("ȸ������ �Ϸ�");
		setLabelForm(jl);
		jd.getContentPane().add(jl);
		jb = new JButton("Ȯ��");
		jb.setBackground(MyColor.w_white);
		jd.getContentPane().add(jb);
		jd.setVisible(false);

		setModal(true);
	}

	@Override
	public void receive(PacketBase packet) {

		if (packet.getClass() == ScSignUpAck.class) {
			ScSignUpAck ack = (ScSignUpAck) packet;

			if (ack.eResult == EResult.SUCCESS) {// ȸ������ ����
				idchk = false;
				hpchk = false;

				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jd.setVisible(false);
						dispose();
					}
				});
				jd.setModal(true);
				jd.setVisible(true);
			} else if (ack.eResult == EResult.DUPLICATEED_ID) {
				AlreadyUsePop pop = new AlreadyUsePop("�ߺ��� ID�Դϴ�");
			} else if (ack.eResult == EResult.DUPLICATEED_PHONE) {
				AlreadyUsePop pop = new AlreadyUsePop("�ߺ��� �޴�����ȣ �Դϴ�");
			} else {
				System.out.println("ȸ������ ����");
			}
		} else if (packet.getClass() == ScDuplicateIDAck.class) {// �ߺ�Ȯ��üũ
			ScDuplicateIDAck ack = (ScDuplicateIDAck) packet;
			if ((ack.eResult == EResult.SUCCESS) && !ack.is_hp) {// id����
				idchk = true;
				jl2.setText("��밡���� id �Դϴ�");
				loginChk();
			} else if ((ack.eResult == EResult.SUCCESS) && ack.is_hp) {// �ڵ�������
				hpchk = true;
				jl2.setText("��� ������ �ڵ��� �Դϴ�.");
				loginChk();
			} else if ((ack.eResult == EResult.DUPLICATEED_ID) && !ack.is_hp) {// id�ߺ�
				jl2.setText("�ߺ��� ID �Դϴ�.");
				loginChk();
			} else if ((ack.eResult == EResult.DUPLICATEED_ID) && ack.is_hp) {// hp�ߺ�
				jl2.setText("�ߺ��� �ڵ��� �Դϴ�.");
				loginChk();
			}
		} else {
			System.out.println("SignUpMain ����");
		}
	}

	void check() {

		name = nameTextField.getText();
		id = idTextField.getText();
		pass = passwordField.getText();
		phoneNum = phoneNumTextField.getText();
		pass2 = check_passwordField.getText();
		try {
			if (name.matches(korean)) {
				label_1.setText("�Է� Ȯ��");
			} else {
				label_1.setText("�ѱ��� �ƴմϴ�.");
				AlreadyUsePop pop = new AlreadyUsePop("��Ȯ�� ������  �Է��ϼ���");
				return;
			}
			if (id.matches(eng) && id.matches(num)) {
				label_2.setText("�Է� Ȯ��");
			} else {
				label_2.setText("ID ���� ����");
				AlreadyUsePop pop = new AlreadyUsePop("��Ȯ�� ������  �Է��ϼ���");
				return;
			}
			if (pass.matches(eng) && pass.matches(num)) {
				label_3.setText("�Է� Ȯ��");
			} else {
				label_3.setText("��й�ȣ ���� ����");
				AlreadyUsePop pop = new AlreadyUsePop("��Ȯ�� ������  �Է��ϼ���");
				return;
			}
			if (phoneNum.matches(phoneChk)) {
				label_5.setText("�Է� Ȯ��");
			} else {
				label_5.setText("�޴��� ��ȣ ���� ����");
				AlreadyUsePop pop = new AlreadyUsePop("��Ȯ�� ������  �Է��ϼ���");
				return;
			}
			if (pass.matches(pass2)) {
				label_4.setText("�Է� Ȯ��");
			} else {
				label_4.setText("��й�ȣ ����ġ");
				AlreadyUsePop pop = new AlreadyUsePop("��Ȯ�� ������  �Է��ϼ���");
				return;
			}

			if (idchk && hpchk) {
				CsSignUpSyn packet = new CsSignUpSyn(nameTextField.getText(), idTextField.getText(),
						passwordField.getText(), phoneNumTextField.getText(), "", 0);
				ClientNet.getInstance().sendPacket(packet);
				idchk = false;
				hpchk = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ϸ�ġ ����");
		}
	}

	public void infoChk() {
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkSignUp.setVisible(false);
			}
		});
		chkSignUp.setModal(true);
		chkSignUp.setVisible(true);
	}

	void idChk() {
		id = idTextField.getText();
		if (id.matches(eng) && id.matches(num) && id.length() < 9 && !chkJd.isVisible()) {
			CsDuplicateIDSyn packet = new CsDuplicateIDSyn(idTextField.getText(), false);
			ClientNet.getInstance().sendPacket(packet);
		} else {
			chkJd.setModal(true);
			chkJd.setVisible(true);
		}
	}

	void phChk() {
		phoneNum = phoneNumTextField.getText();
		if (phoneNum.matches(phoneChk)) {
			CsDuplicateIDSyn packet = new CsDuplicateIDSyn(phoneNumTextField.getText(), true);
			ClientNet.getInstance().sendPacket(packet);
		} else {
			chkJd.setModal(true);
			chkJd.setVisible(true);
		}
	}

	void loginChk() {

		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd2.setVisible(false);
			}
		});
		jd2.setModal(true);
		jd2.setVisible(true);
	}

	public void textFieldSet() {// ȸ������ �ؽ�Ʈ �ʵ�,�󺧳��� �ʱ�ȭ
		for (JTextField text : textList) {
			text.setText("");
		}

		for (JPasswordField pText : pTextList) {
			pText.setText("");
		}

		label_1.setText("�ѱ۷� �Է��ϼ���");
		label_2.setText("����,���ڷ� ���յ� 8�ڸ����Ϸ� �Է��ϼ���");
		label_3.setText("����,���ڷ� ���յ� 8�ڸ��� �Է��ϼ���");
		label_4.setText("�Է��� ��й�ȣ�� ���� �Է��ϼ���");
		label_5.setText("'-'�� �����ϰ� �Է��ϼ���");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTextField jtf = (JTextField) e.getSource();

		if (!jtf.getText().equals("")) {
			jtf.setText("");
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn.getText().equals("ID �ߺ�üũ")) {
			if (!chkJd.isVisible())
				idChk();
		} else if (btn.getText().equals("�޴��� �ߺ�üũ")) {
			if (!chkJd.isVisible())
				phChk();
		} else if (btn.getText().equals("���")) {
			dispose();
		} else if (btn.getText().equals("ȸ������")) {
			if (!idchk || !hpchk) {
				infoChk();
			} else {
				check();
			}
		} else {
			System.out.println("��ư �Է� ����");
		}
	}

	// �� ���� �ٲٴ� �żҵ�
	JLabel setLabelForm(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBackground(MyColor.black);
		label.setForeground(MyColor.white);
		return label;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		String text = e.getKeyChar() + "";
		if (text.matches(error)) {
			label_1.setText("�ѱ��� �ƴմϴ�.");
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			label_1.setText("�ѱ۷� �Է��ϼ���");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}