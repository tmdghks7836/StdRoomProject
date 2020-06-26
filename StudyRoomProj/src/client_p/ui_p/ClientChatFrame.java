package client_p.ui_p;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client_p.ClientNet;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsChatSyn;
import data_p.user_p.UserData;
import packetBase_p.PacketBase;
import server_p.packet_p.broadCast.ScChatBroadCast;
import server_p.packet_p.syn_p.ScChatSyn;

public class ClientChatFrame extends JPanel implements Receivable {

	private final static String newline = "\n";
	String text = "";
	JFrame window;
	CsChatSyn chatSyn = null;
	TextArea textArea;
	private JScrollPane scrollPane_Chat;

	public ClientChatFrame() {
		window = new JFrame();
		window.setBounds(100, 100, 900, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		window.getContentPane().add(this);

		textArea = new TextArea();
		textArea.setBounds(0, 0, 458, 478);
		scrollPane_Chat = new JScrollPane(textArea);
		add(scrollPane_Chat);

		TextField keyChat = new TextField();
		keyChat.setBounds(0, 484, 337, 182);
		add(keyChat);
		keyChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				text = "["+BaseFrame.getInstance().userData.name+"]: "+keyChat.getText() + "\n";
				textArea.append(text);
				chatSyn.setText(text);
				ClientNet.getInstance().sendPacket(chatSyn);
				
				keyChat.setText("");
				
				keyChat.selectAll();
				scrollPane_Chat.getVerticalScrollBar().setValue(scrollPane_Chat.getVerticalScrollBar().getMaximum());
				
//				text = keyChat.getText();
//				chatSyn.setText(text);
//				ClientNet.getInstance().sendPacket(chatSyn);
//				keyChat.selectAll();
//				textArea.setCaretPosition(textArea.getParent().getWidth());
			}
		});

		keyChat.setColumns(10);

		JButton sendButton = new JButton("�� ��");
		sendButton.setBounds(343, 484, 99, 77);
		add(sendButton);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!keyChat.getText().equals("")) {

					text = "["+BaseFrame.getInstance().userData.name+"]: "+keyChat.getText() + "\n";
					textArea.append(text);
					chatSyn.setText(text);
					ClientNet.getInstance().sendPacket(chatSyn);
					
					keyChat.setText("");
					
					keyChat.selectAll();
					scrollPane_Chat.getVerticalScrollBar().setValue(scrollPane_Chat.getVerticalScrollBar().getMaximum());
					
					
//					text = keyChat.getText();
//					chatSyn.setText(text);
//					ClientNet.getInstance().sendPacket(chatSyn);
//					keyChat.selectAll();
//					textArea.setCaretPosition(textArea.getParent().getWidth());
//					keyChat.setText("");
				}
			}
		});

		JButton exitButton = new JButton("�� ��");
		exitButton.setBounds(343, 559, 99, 77);
		add(exitButton);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				BaseFrame.getInstance().view("MainLayout");
			}
		});

		setVisible(true);
	}

	public void setChatPacket(CsChatSyn chatSyn) {
		this.chatSyn = chatSyn;
	}

	@Override
	public void receive(PacketBase packet) {

		ScChatBroadCast scChat = (ScChatBroadCast) packet;
		textArea.setText(textArea.getText() + newline + scChat.getText() + newline);

	}

}