package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Controller.Controller;

public class Table_Panel {
	private JButton CREATE, LOGOUT;
	private JPanel panel, panel1;
	private JList<String> tablelist;
	private JScrollPane tbscr;
	private JLabel label3;

	Table_Panel(Controller c, String dbname) {
		panel = new JPanel();

		String s[] = new String[100];
		int i = 0;
		c.cConnectDB(dbname);
		ResultSet rs = c.cShowTables();
		try {
			if (rs.first() == false) {
			} else {
				do {
					s[i] = rs.getString("Tables_in_" + dbname);
					i++;
				} while (rs.next());
			}
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}

		tablelist = new JList<String>(s);
		tablelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbscr = new JScrollPane(tablelist);
		tbscr.setPreferredSize(new Dimension(150, 50));

		label3 = new JLabel();
		label3.setText("TABLES");
		label3.setAlignmentX(Component.CENTER_ALIGNMENT);

		CREATE = new JButton("New Table");
		CREATE.setAlignmentX(Component.CENTER_ALIGNMENT);

		LOGOUT = new JButton("Logout");

		panel1 = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(label3);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(tbscr);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));

		panel1.add(CREATE);
		
		panel1.add(LOGOUT);
		
		panel1.setMaximumSize(new Dimension(500, 50));

		panel.add(panel1);
		
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		CREATE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateTablePanel obj = new CreateTablePanel(c, dbname);
				c.cRemoveandadd(panel, obj.getPanel(), c.getFrame());
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

		tablelist.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					String db = tablelist.getSelectedValue();
					c.cRemoveandadd(panel, new CRUD_Panel(c, db).getPanel(), c.getFrame());
				}
			}
		});
	}

	JPanel getPanel() {
		return panel;
	}
}