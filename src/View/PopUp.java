package View;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PopUp {
	public void showPopUp(String s) {
		JDialog dialog = new JDialog();
		JLabel l = new JLabel();
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JButton ok = new JButton("OK");
		dialog.setSize(new Dimension(200, 100));
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(new GridLayout(2, 2));
		l.setText(s);
		p1.setLayout(new FlowLayout());
		p1.add(l);
		p.setLayout(new FlowLayout());
		p.add(ok);
		dialog.add(p1);
		dialog.add(p);
		ActionListener okb = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		};
		ok.addActionListener(okb);
		dialog.setVisible(true);
	}
	
	public void showPopUp(String s, int i, int j) {
		JDialog dialog = new JDialog();
		JLabel l = new JLabel();
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JButton ok = new JButton("OK");
		dialog.setSize(new Dimension(i, j));
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(new GridLayout(2, 2));
		l.setText(s);
		p1.setLayout(new FlowLayout());
		p1.add(l);
		p.setLayout(new FlowLayout());
		p.add(ok);
		dialog.add(p1);
		dialog.add(p);
		ActionListener okb = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		};
		ok.addActionListener(okb);
		dialog.setVisible(true);
	}
}