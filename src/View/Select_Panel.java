package View;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Controller;

public class Select_Panel {
	private JPanel panel;
	private JTable jTable = new JTable();
	private TitledBorder title;
	private JScrollPane jscp;


	public Select_Panel(Controller c, String tbname) {
		
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "SELECT");
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
			
			jscp = new JScrollPane(jTable);
			
			panel.add(jscp);
			
			
		}catch (Exception e) {

		}
	}

	JPanel getPanel() {
		return panel;
	}
}
