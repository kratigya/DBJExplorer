package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import Controller.Controller;

public class CRUD_Panel {
	private JPanel panel, panel1, panel2;
	private JTabbedPane jtp;
	private JButton home, LOGOUT;
	private TitledBorder title;

	public CRUD_Panel(Controller c, String tbname) {
		
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Table Name : "+tbname);
		title.setTitleJustification(TitledBorder.CENTER);
		
		
		jtp = new JTabbedPane();
		jtp.addTab("INSERT", new Insert_Panel(c, tbname).getPanel());
		jtp.addTab("UPDATE", new Update_Panel(c, tbname).getPanel());
		jtp.addTab("DELETE", new Delete_Panel(c, tbname).getPanel());
		jtp.addTab("SELECT", new Select_Panel(c, tbname).getPanel());
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.setBorder(title);;
		panel.add(jtp);

		LOGOUT = new JButton("Logout");
		home = new JButton("Home");

		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel1 = new DB_panel(c).getPanel();
				c.cRemoveandadd(panel, panel1, c.getFrame());
			}
		});

		LOGOUT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.cReleaseresources();
				c.cShowPopUp("LOGGED OUT");
				c.cRemoveandadd(panel, new Login_panel(c).getPanel(), c.getFrame());
			}
		});

		panel2 = new JPanel();
		panel2.add(home);
		panel2.add(LOGOUT);
		panel.add(panel2);
		
		c.cSetSize(new Dimension(700, 500));
	}

	JPanel getPanel() {
		return panel;
	}

	void setPanel(int i) {
		jtp.setSelectedIndex(i);
	}
}
