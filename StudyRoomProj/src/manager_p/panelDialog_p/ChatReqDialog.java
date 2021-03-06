package manager_p.panelDialog_p;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client_p.ClientNet;
import client_p.packet_p.syn_p.CsChatSyn;
import client_p.ui_p.MyColor;
import manager_p.ManagerWindow;
import manager_p.ack_p.MsChatConnectAck;
import server_p.packet_p.syn_p.SMChatConnectSyn;
import java.awt.Color;

public class ChatReqDialog extends JDialog {
	ManagerWindow mw;
	private final JPanel contentPanel = new JPanel();
	SMChatConnectSyn smc;
	String userName = null;
	public JLabel lbClientName;

	public static void main(String[] args) {
		ChatReqDialog dialog = new ChatReqDialog(new ManagerWindow(), new SMChatConnectSyn(null, null));
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	public ChatReqDialog(ManagerWindow mw, SMChatConnectSyn smc) {
		this.mw = mw;
		this.smc = smc;
//		userName = smc.name;
		setBounds(100, 100, 339, 213);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uCC44\uD305\uC744 \uC218\uB77D\uD558\uC2DC\uACA0\uC2B5\uB2C8\uAE4C?");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(42, 85, 221, 15);
		contentPanel.add(lblNewLabel);

		lbClientName = new JLabel(userName);
		lbClientName.setForeground(Color.WHITE);
		lbClientName.setBounds(42, 35, 129, 15);
		contentPanel.add(lbClientName);

		JLabel lblNewLabel_2 = new JLabel(
				"\uB2D8 \uC73C\uB85C\uBD80\uD130 \uCC44\uD305 \uBB38\uC758 \uC694\uCCAD\uC774 \uC654\uC2B5\uB2C8\uB2E4.");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(42, 60, 265, 15);
		contentPanel.add(lblNewLabel_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(MyColor.w_white);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						MsChatConnectAck packet = new MsChatConnectAck(true);
						CsChatSyn csc = new CsChatSyn();
						mw.pnl_Chatting.btnTerminate.setEnabled(true);
						mw.pnl_Chatting.amIstopChat = false;
						mw.pnl_Chatting.chatSyn = csc;
						ClientNet.getInstance().sendPacket(packet);
						mw.pnl_Chatting.textArea.setText("");
						mw.pnl_Chatting.textField.setText("");
						mw.tabbedPane.setSelectedIndex(1);
						mw.pnl_Chatting.lb_Chat_name.setText("������ [" + mw.pnl_Chatting.userName + "]");
						mw.pnl_Chatting.lb_Chat_end.setText(mw.pnl_Chatting.chatStart);
						mw.pnl_Chatting.isChatting = true;
                        
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\uAC70\uC808");
				cancelButton.setBackground(MyColor.w_white);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						MsChatConnectAck packet = new MsChatConnectAck(false);
						ClientNet.getInstance().sendPacket(packet);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
