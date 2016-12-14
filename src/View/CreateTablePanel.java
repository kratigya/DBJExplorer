package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Controller.Controller;

public class CreateTablePanel {
	private int nul, count = -1;
	private String query = "", query1, query2, query3;
	private JButton INT, DECIMAL, TIME, DATE, VARCHAR, CHAR, BUILD, EXECUTE, ADDC, ADD;
	private JLabel cblabel, tblabel;
	private JTextField cbname, tbname, bquery;
	private JPanel panel, panel1, panel2, panel3, panel4, panel5, panel6, panel7;
	private Controller c;
	private TitledBorder title;
	
	public CreateTablePanel(Controller c, String dbname) {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Create Table");
		title.setTitleJustification(TitledBorder.CENTER);

		panel.setBorder(title);

		panel1 = new JPanel();
		
		tbname = new JTextField(10);
		tblabel = new JLabel("TABLE NAME:");

		panel1.add(tblabel);
		panel1.add(tbname);

		c.cSetSize(new Dimension(480, 200));

		ADDC = new JButton("Add Column");
		panel5 = new JPanel();
		panel5.add(ADDC);

		ADDC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				nul = 0;
				count++;
				JFrame addframe = new JFrame();

				panel2 = new JPanel();
				panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

				panel3 = new JPanel();

				cbname = new JTextField(10);
				cblabel = new JLabel("COLUMN NAME:");
				panel3.add(cblabel);
				panel3.add(cbname);

				panel2.add(panel3);

				panel4 = new JPanel();
				panel4.setLayout(new FlowLayout());

				INT = new JButton("INT");
				DECIMAL = new JButton("DECIMAL");
				TIME = new JButton("TIME");
				DATE = new JButton("DATE");
				CHAR = new JButton("CHAR");
				VARCHAR = new JButton("VARCHAR");

				INT.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const1(cbname.getText(), "INT");
					}
				});

				DECIMAL.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const1(cbname.getText(), "DECIMAL");
					}
				});

				TIME.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const1(cbname.getText(), "TIME");
					}
				});

				DATE.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const1(cbname.getText(), "DATE");
					}
				});

				CHAR.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const2(cbname.getText(), "CHAR");
					}
				});

				VARCHAR.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						const2(cbname.getText(), "VARCHAR");
					}
				});

				panel4.add(INT);
				panel4.add(DECIMAL);
				panel4.add(TIME);
				panel4.add(DATE);
				panel4.add(CHAR);
				panel4.add(VARCHAR);
				title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Data Types");
				title.setTitleJustification(TitledBorder.CENTER);
				panel4.setBorder(title);
				panel2.add(panel4);

				ADD = new JButton("Add");
				ADD.setAlignmentX(Component.CENTER_ALIGNMENT);

				panel2.add(ADD);
				panel2.add(Box.createRigidArea(new Dimension(0, 10)));
				ADD.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						addframe.setVisible(false);
						addframe.dispose();

					}
				});

				addframe.add(panel2);
				addframe.setSize(new Dimension(500, 170));
				addframe.setLocationRelativeTo(null);
				addframe.getRootPane().setDefaultButton(ADD);
				addframe.setVisible(true);
			}
		});

		BUILD = new JButton("Build Query");
		bquery = new JTextField(40);
		panel.add(panel1);
		panel.add(panel5);

		EXECUTE = new JButton("Execute");
		panel6 = new JPanel();
		panel6.add(BUILD);
		panel6.add(EXECUTE);
		panel7 = new JPanel();
		panel7.add(bquery);
		panel.add(panel6);
		panel.add(panel7);

		BUILD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				query2 = query;
				query2 += ");";
				query1 = "CREATE TABLE " + tbname.getText() + "( ";
				query1 += query2;
				bquery.setText(query1);
			}
		});
		EXECUTE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
					query3 = bquery.getText();
					c.cCreateTable(query3);
					c.cRemoveandadd(panel, new Table_Panel(c, dbname).getPanel(), c.getFrame());
			}
		});
	}

	void const1(String cname, String type) {
		JFrame frame = new JFrame();

		JButton add = new JButton("ADD");
		JPanel panel1 = new JPanel(), panel2 = new JPanel(), panel3 = new JPanel(), panel4 = new JPanel(),
				panel5 = new JPanel();
		JLabel check = new JLabel("CHECK"), from = new JLabel("FROM:"), to = new JLabel("TO:"),
				def = new JLabel("DEFAULT");
		JTextField fromt = new JTextField(5), tot = new JTextField(5), deft = new JTextField(5);

		panel1.add(def);
		panel1.add(deft);

		panel2.add(check);
		panel2.add(from);
		panel2.add(fromt);
		panel2.add(to);
		panel2.add(tot);

		JCheckBox notnull = new JCheckBox("NOT NULL");

		notnull.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					nul = 1;
				else
					nul = 0;
			}
		});

		panel3.add(notnull);
		panel4.add(add);
		add.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		panel5.add(panel1);
		panel5.add(panel2);
		panel5.add(panel3);
		panel5.add(panel4);

		frame.add(panel5);

		ActionListener addb = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count == 0)
					query = query + cname + " " + type;
				else
					query = query + ", " + cname + " " + type;

				if (nul == 1)
					query += " NOT NULL ";
				if (deft.getText().length() != 0)
					if (type.equals("TIME") || type.equals("DATE"))
						query += " DEFAULT '" + deft.getText() + "' ";
					else
						query += " DEFAULT " + deft.getText() + " ";
				if (fromt.getText().length() != 0 || fromt.getText().length() != 0) {
					query += " CHECK (";
					if (fromt.getText().length() != 0 && tot.getText().length() != 0)
						query += cname + " > " + fromt.getText() + " AND " + cname + " < " + tot.getText();
					else if (fromt.getText().length() != 0)
						query += cname + " > " + fromt.getText();
					else
						query += cname + " < " + tot.getText();

					query += " ) ";
				}
				frame.setVisible(false);
				frame.dispose();
			}
		};

		add.addActionListener(addb);

		frame.getRootPane().setDefaultButton(ADD);
		frame.setSize(300, 175);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void const2(String cname, String type) {
		JFrame frame = new JFrame();
		JButton add = new JButton("ADD");
		JPanel panel1, panel2, panel3, panel4, panel5;
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		JLabel size = new JLabel("SIZE"), def = new JLabel("DEFAULT");
		JTextField sizet = new JTextField(5), deft = new JTextField(5);

		panel1.add(def);
		panel1.add(deft);

		panel2.add(size);
		panel2.add(sizet);

		JCheckBox notnull = new JCheckBox("NOT NULL");

		notnull.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					nul = 1;
				else
					nul = 0;
			}
		});

		panel3.add(notnull);
		panel4.add(add);
		add.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel5.add(panel1);
		panel5.add(panel2);
		panel5.add(panel3);
		panel5.add(panel4);
		panel5.add(Box.createRigidArea(new Dimension(0, 10)));

		frame.add(panel5);
		ActionListener addb = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count == 0)
					query = query + cname + " " + type;
				else
					query = query + ", " + cname + " " + type;
				if (sizet.getText().equals(""))
					c.cShowPopUp("ENTER SIZE");
				else
					query = query + "(" + sizet.getText() + ") ";
				if (nul == 1)
					query = query + " NOT NULL ";
				if (deft.getText().length() != 0)
					query = query + " DEFAULT '" + deft.getText() + "' ";
				frame.setVisible(false);
				frame.dispose();
			}
		};

		add.addActionListener(addb);

		frame.getRootPane().setDefaultButton(ADD);
		frame.setSize(200, 175);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	JPanel getPanel() {
		return panel;
	}
}