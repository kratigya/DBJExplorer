package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;

public class Delete_Panel {
	private JTable jTable1 = new JTable();
	private String query = "" , query1 = "";
	private JScrollPane jscr;
	private JPanel panel;
	private JButton Build, Execute;
	private JTextField query_TF;
	private TitledBorder title;
	

	public Delete_Panel(Controller c, String tbname) {
		
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "DELETE");
		title.setTitleJustification(TitledBorder.CENTER);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(title);

		try {
			ResultSet rs = c.cExecuteQueryrs("SELECT * FROM " + tbname + ";", "DEFAULT");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// for changing column and row model
			DefaultTableModel tm = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			jTable1.setModel(tm);
			jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// clear existing columns
			tm.setColumnCount(0);

			// add specified columns to table
			for (int i = 1; i <= columnCount; i++) {
				tm.addColumn(rsmd.getColumnName(i));
			}

			// clear existing rows
			tm.setRowCount(0);

			// add rows to table
			while (rs.next()) {
				String[] a = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					a[i] = rs.getString(i + 1);
				}
				tm.addRow(a);
			}
			tm.fireTableDataChanged();

			Build = new JButton("Build Query");
			Build.setAlignmentX(Component.CENTER_ALIGNMENT);

			Execute = new JButton("Execute");
			Execute.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			query_TF = new JTextField();

			jscr = new JScrollPane(jTable1);

			panel.add(jscr);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(Build);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(Execute);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(query_TF);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			
			Build.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object data = new Object();
					query = "DELETE FROM " + tbname + " WHERE ";
					int j = jTable1.getSelectedRow();
					int i = 0;
					for (i = 0; i <= columnCount - 1; i++) {
						data = (Object) jTable1.getValueAt(j, i);
							if (data == null) {
								query = query + jTable1.getColumnName(i) + " IS NULL AND ";
							} else {
								if (c.cGetDataType(tbname, jTable1.getColumnName(i)).equals("int")
										|| c.cGetDataType(tbname, jTable1.getColumnName(i)).equals("decimal")) {
									query = query + jTable1.getColumnName(i) + "=" + data + " AND ";
								} else {
									query = query + jTable1.getColumnName(i) + "='" + data + "' AND ";
								}
							}
					}
					query1 = query.substring(0, query.length() - 4);
					query1+=";";
					query_TF.setText(query1);
				}
			});
			
			Execute.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					query = query_TF.getText();
					c.cExecuteQuery(query, "DELETE_QUERY");
					CRUD_Panel obj = new CRUD_Panel(c, tbname);
					obj.setPanel(2);
					c.cRemoveandadd(panel, obj.getPanel(), c.getFrame());
				}
			});
			// Close ResultSet and Statement
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	JPanel getPanel() {
		return panel;
	}
}