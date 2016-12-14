package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import Controller.Controller;

public class Insert_Panel{
	private JPanel panel;
	private JList<String> columnlist;
	private JButton Execute,Build;
	private JScrollPane col;
	private String value,query;
	private HashMap<String,String> cv;
	private int j;
	private TitledBorder title;
	private JComboBox<String> jcb;
	
	public Insert_Panel(Controller c,String tbname) {
		cv=new HashMap<String,String>();
		j=0;
		panel = new JPanel();
		panel.setSize(400, 400);
		ResultSet rs = c.cDesc("DESCRIBE " + tbname + ";");
		String s[] = new String[100];
		int i = 0;
		try {
			rs.first();
			do {
				s[i] = rs.getString(1);
				i++;
			} while (rs.next());
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(),1500,100);
		}
		columnlist = new JList<String>(s);
		columnlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		col = new JScrollPane(columnlist);
		
		Execute = new JButton("Execute");
		Build = new JButton("Build");		
		
		ResultSet rs1 = c.cretrieveQuery("INSERT_QUERY");
		String[] qlist = new String[100];
		int pos=0;
		try {
			while(rs1.next()){
				String str = rs1.getString("QUERY");
				qlist[pos++]=str;
			}
		} catch (SQLException e1) {
			PopUp obj = new PopUp();
			obj.showPopUp(e1.getMessage(), 1500, 100);
		}
		jcb = new JComboBox<String>();
		jcb.setModel(new DefaultComboBoxModel<String>(qlist));
		jcb.setEditable(true);
		jcb.setSelectedItem((Object) "HISTORY");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(col);
		col.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(Build);
		Build.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(Execute);
		Execute.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(jcb);
		jcb.setMaximumSize(new Dimension(700, 100));
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
				
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "INSERT");
		title.setTitleJustification(TitledBorder.CENTER);
		
		panel.setBorder(title);
		columnlist.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
						String cname = columnlist.getSelectedValue();
						value = new String();
						JFrame frame = new JFrame();
						JPanel panel = new JPanel();
						JPanel panel1 = new JPanel();
						JLabel label;
						
						label = new JLabel("VALUE"+ " (TYPE: "+ c.cGetDataType(tbname, cname)+")");
						JTextField field = new JTextField(20);
						JButton DONE = new JButton("DONE");

						panel.add(label);
						panel.add(field);
						panel1.add(DONE);
						panel.add(panel1);
						
						frame.add(panel);
						frame.setSize(300, 120);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						DONE.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								value = field.getText();
								cv.put(cname, value);
								j++;
								frame.setVisible(false);
							}
						});
				}
			}
		});
		
		Build.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				query = "INSERT INTO "+tbname+" (";
				Map.Entry<String,String> pair;
				Iterator<Entry<String, String>> it = cv.entrySet().iterator();
				for(int i=0;i<j;i++)	{
					pair = it.next();
					if(i==0)	query += " " + pair.getKey();
					else	query += ", " + pair.getKey(); 
				}
				query += ") VALUES (";
				it = cv.entrySet().iterator();
				for(int i=0;i<j;i++)	{
					pair = it.next();
					if(i==0)	{
						
							if(c.cGetDataType(tbname, pair.getKey()).equals("int") || c.cGetDataType(tbname, pair.getKey()).equals("decimal"))
								query += " " + pair.getValue();
							else
								query += " '" + pair.getValue() + "' ";
						
					}
					else	{
						
							if(c.cGetDataType(tbname, pair.getKey()).equals("int") || c.cGetDataType(tbname, pair.getKey()).equals("decimal"))
								query += ", " + pair.getValue();
							else
								query += ", '" + pair.getValue() + "' ";
						
					}
				}
				query = query + ");";
				jcb.setSelectedItem((Object) query);
			}
		});
		
		Execute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String queryfinal = jcb.getSelectedItem().toString();
				c.cExecuteQuery(queryfinal, "INSERT_QUERY");
				cv.clear();
				j=0;
				
				CRUD_Panel obj = new CRUD_Panel(c, tbname);
				obj.setPanel(0);
				c.cRemoveandadd(panel, obj.getPanel(), c.getFrame());
			}
		});
	}
	
	JPanel	getPanel()	{
		return panel;
	}
}