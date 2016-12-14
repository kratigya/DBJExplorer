package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.Controller;
import Model.User;

public class Login_panel {
	private JButton LOGIN, SIGNUP;
	private JPanel panel, panel2, panel3, panel4, panel5;
	private JLabel label1, label2, label3;
	private JTextField text1, text2;
	private TitledBorder title;

	Login_panel(Controller c) {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		label1 = new JLabel();

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "User Login");
		title.setTitleJustification(TitledBorder.CENTER);

		panel2.setLayout(new FlowLayout());
		panel3.setLayout(new FlowLayout());
		panel4.setLayout(new FlowLayout());
		panel5.setLayout(new FlowLayout());
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		label3 = new JLabel();
		label3.setText("User Login");

		LOGIN = new JButton("LOGIN");
		SIGNUP = new JButton("SIGNUP");

		panel.setBorder(title);
		panel3.add(label1);
		panel3.add(text1);
		panel3.add(label2);
		panel3.add(text2);
		panel3.add(LOGIN);
		panel3.add(SIGNUP);
		panel.add(panel3, BorderLayout.CENTER);
		c.cSetSize(new Dimension(300, 150));

		ActionListener login = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User u1 = new User(text1.getText(), text2.getText());
				if (c.cValidUser(u1)) {
						boolean i = c.setUser(u1);
						if(i==true)	c.cRemoveandadd(panel, new Dashboard(c).getPanel(), c.getFrame());
				}
			}
		};

		ActionListener signup = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					User u1 = new User(text1.getText(), text2.getText());
					boolean i;
					if (c.cNewUser(u1)) {
						i = c.setUser(u1);
						if(i==true)	c.cRemoveandadd(panel, new Dashboard(c).getPanel(), c.getFrame());
					} else {
						c.cShowPopUp("ALREADY EXISTS");
					}
			}
		};

		LOGIN.addActionListener(login);
		SIGNUP.addActionListener(signup);
		c.getFrame().getRootPane().setDefaultButton(LOGIN);
	}

	JPanel getPanel() {
		return panel;
	}
}