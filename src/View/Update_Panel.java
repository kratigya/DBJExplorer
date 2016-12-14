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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;

public class Update_Panel {
	private JPanel panel;
	private JTable jTable = new JTable();
	private JScrollPane jscp;
	private JComboBox<String> jcb;
	private JButton Build, Execute, Upd;
	private String value, query, query1;
	private TitledBorder title;

	Update_Panel(Controller c, String tbname) {
		
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "UPDATE");
		title.setTitleJustification(TitledBorder.CENTER);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(title);
		
		try {
			ResultSet rs = c.cExecuteQueryrs("SELECT * FROM " + tbname + ";", "SELECT_QUERY");
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// for changing column and row model
			DefaultTableModel tm = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			jTable.setModel(tm);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

			Upd = new JButton("Update");
			Upd.setAlignmentX(Component.CENTER_ALIGNMENT);

			Build = new JButton("Build");
			Build.setAlignmentX(Component.CENTER_ALIGNMENT);

			Execute = new JButton("Execute");
			Execute.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			ResultSet rs1 = c.cretrieveQuery("UPDATE_QUERY");
			String[] qlist = new String[100];
			int i=0;
			while(rs1.next()){
				String str = rs1.getString("QUERY");
				qlist[i++]=str;
			}
			jcb = new JComboBox<String>();
			jcb.setModel(new DefaultComboBoxModel<String>(qlist));
			jcb.setEditable(true);
			jcb.setSelectedItem((Object) "HISTORY");
			
			jscp = new JScrollPane(jTable);

			panel.add(jscp);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(Upd);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(Build);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(Execute);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(jcb);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));

			jcb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
				}
			});
			
			Upd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame frame2 = new JFrame();
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					Object data = new Object();

					try {

						int i = jTable.getSelectedRow();
						int j = jTable.getSelectedColumn();
						data = (Object) jTable.getValueAt(i, j);

						JPanel panel1 = new JPanel();
						JPanel panel2 = new JPanel();

						JButton update = new JButton("Update");
						update.setAlignmentX(Component.CENTER_ALIGNMENT);

						JLabel new_value = new JLabel();
						new_value.setText("New Value");

						JLabel prev_value = new JLabel();
						prev_value.setText("Previous Value");

						JTextField text1 = new JTextField(20);
						text1.setText((String) data);
						text1.setEditable(false);

						JTextField text2 = new JTextField(20);

						panel1.add(prev_value);
						panel1.add(text1);
						panel2.add(new_value);
						panel2.add(text2);

						panel.add(panel1);
						panel.add(panel2);
						panel.add(update);
						panel.add(Box.createRigidArea(new Dimension(0, 5)));

						frame2.add(panel);
						frame2.setSize(new Dimension(300, 150));
						frame2.getRootPane().setDefaultButton(update);
						frame2.setLocationRelativeTo(null);
						frame2.setVisible(true);
						update.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								value = text2.getText();

								query = "UPDATE " + tbname + " SET " + jTable.getColumnName(j) + "= ";
								if (c.cGetDataType(tbname, jTable.getColumnName(j)).equals("int")
										|| c.cGetDataType(tbname, jTable.getColumnName(j)).equals("decimal")) {
									query += value;
								} else if (value.equals("")) {
									query += " NULL ";
								} else {
									query += "'" + value + "'";
								}
								query += " WHERE ";
								Object data = new Object();
								for (int j = 0; j <= columnCount - 1; j++) {
									data = (Object) jTable.getValueAt(i, j);

									if (data == null) {

										query = query + jTable.getColumnName(j) + " IS NULL AND ";
									} else {
										if (c.cGetDataType(tbname, jTable.getColumnName(j)).equals("int")
												|| c.cGetDataType(tbname, jTable.getColumnName(j)).equals("decimal")) {
											query = query + jTable.getColumnName(j) + "=" + data + " AND ";
										} else {
											query = query + jTable.getColumnName(j) + "='" + data + "' AND ";
										}
									}
								}
								query1 = query.substring(0, query.length() - 4);
								query1 += " ;";

								frame2.setVisible(false);
								frame2.dispose();
							}
						});
					} catch (ArrayIndexOutOfBoundsException e1) {
						c.cShowPopUp("Select the entry to update");
					}
				}
			});

			Build.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					jcb.setSelectedItem((Object) query1);
				}
			});

			Execute.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					query = jcb.getSelectedItem().toString();
					c.cExecuteQuery(query, "UPDATE_QUERY");
					CRUD_Panel obj = new CRUD_Panel(c, tbname);
					obj.setPanel(1);
					c.cRemoveandadd(panel, obj.getPanel(), c.getFrame());
				}
			});

		} catch (Exception e) {

		}
	}

	JPanel getPanel() {
		return panel;
	}
}