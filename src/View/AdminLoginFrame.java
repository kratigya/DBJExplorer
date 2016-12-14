package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.Controller;
import Model.User;

public class AdminLoginFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JButton SUBMIT;
	private JPanel panel, panel1;
	private JLabel label1, label2, label3;
	private JTextField text1, text2;
	private User root;
	private Controller obj;
	private TitledBorder title;

	public void admin(Controller obj) {
		this.obj = obj;
		panel1 = new JPanel();

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Admin Login");
		title.setTitleJustification(TitledBorder.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);
		text1.setText("root");
		text1.setEditable(false);

		label2 = new JLabel();
		label2.setText("Password:");

		label3 = new JLabel();
		label3.setText("Admin Login");

		text2 = new JPasswordField(15);

		SUBMIT = new JButton("SUBMIT");

		panel = new JPanel(new BorderLayout());
		panel1.add(label1);
		panel1.add(text1);
		panel1.add(label2);
		panel1.add(text2);
		panel1.add(SUBMIT);
		panel.setBorder(title);
		// panel.add(panel2, BorderLayout.PAGE_START);
		panel.add(panel1, BorderLayout.CENTER);
		getRootPane().setDefaultButton(SUBMIT);
		SUBMIT.addActionListener(this);

		setContentPane(panel);
		setTitle("DBJ Explorer");
		setSize(new Dimension(300, 150));
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		root = new User(text1.getText(), text2.getText());
		boolean rootcon = obj.cConnectRoot(root);
		if (rootcon == true)	{
			panel1 = new Login_panel(obj).getPanel();
			obj.cRemoveandadd(panel, panel1, obj.getFrame());
		}
	}
}