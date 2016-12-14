package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Controller.Controller;

public class Dashboard {

	private JPanel panel, panel1;
	private TitledBorder title;
	private JLabel label2;
	private JButton ENTER;

	public Dashboard(Controller c) {

		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "LOGGED IN");
		title.setTitleJustification(TitledBorder.CENTER);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		label2 = new JLabel(c.cGetUser().toString());
		label2.setFont(new Font("Agency FB", Font.PLAIN, 20));
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(label2);
		ENTER = new JButton("Enter");
		c.cSetSize(new Dimension(700, 125));
		ENTER.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(ENTER);
		panel.setBorder(title);

		ENTER.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel1 = new DB_panel(c).getPanel();
				c.cRemoveandadd(panel, panel1, c.getFrame());
			}
		});

	}

	public JPanel getPanel() {
		return panel;
	}
}
