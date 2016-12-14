package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Controller.Controller;

public class DB_panel {
	private JButton CREATE, LOGOUT;
	private JPanel panel, panel1;
	private JList<String> dblist;
	private JScrollPane dbscr;
	private JLabel label3;

	DB_panel(Controller c) {
		panel = new JPanel();
		String s[] = new String[30];
		int i = 0;
		ResultSet rs = c.cRetrieveDB();
		try {
			if (rs.first() == true) {
				do {
					s[i] = rs.getString("Database");
					i++;
				} while (rs.next());
			}
		} catch (Exception e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}

		dblist = new JList<String>(s);
		dblist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dbscr = new JScrollPane(dblist);
		dbscr.setPreferredSize(new Dimension(100, 50));

		label3 = new JLabel();
		label3.setText("DATABASES");

		CREATE = new JButton("Create a new database");
		CREATE.setAlignmentX(Component.CENTER_ALIGNMENT);

		LOGOUT = new JButton("Logout");

		panel1 = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		label3.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(label3);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(dbscr);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		panel1.add(CREATE);
		panel1.add(LOGOUT);
		panel1.setMaximumSize(new Dimension(500, 50));

		panel.add(panel1);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		c.cSetSize(new Dimension(500, 300));

		LOGOUT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.cReleaseresources();
				c.cShowPopUp("LOGGED OUT");
				c.cRemoveandadd(panel, new Login_panel(c).getPanel(), c.getFrame());
			}
		});

		ActionListener create = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame1 = new JFrame();
				JButton create1 = new JButton("CREATE");
				JLabel dbname = new JLabel("Database name:");
				JTextField text1 = new JTextField(10);
				frame1.setLayout(new FlowLayout());
				frame1.setSize(new Dimension(300, 100));
				frame1.setLocationRelativeTo(null);
				frame1.add(dbname);
				frame1.add(text1);
				frame1.add(create1);
				frame1.setVisible(true);
				frame1.getRootPane().setDefaultButton(create1);
				ActionListener createb = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String db = text1.getText();
						int i = c.cAddDB(db);
						if (i == 1) {
							c.cShowPopUp("CREATED");
							frame1.setVisible(false);
							frame1.dispose();
							c.cRemoveandadd(panel, new DB_panel(c).panel, c.getFrame());
						}
					}
				};
				create1.addActionListener(createb);
			}
		};

		CREATE.addActionListener(create);

		dblist.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					String db = dblist.getSelectedValue();
					c.cRemoveandadd(panel, new Table_Panel(c, db).getPanel(), c.getFrame());
				}
			}
		});

	}

	JPanel getPanel() {
		return panel;
	}
}