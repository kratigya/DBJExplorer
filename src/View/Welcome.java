package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Welcome {

	private JFrame framestart;
	private JLabel label1, label2, label3;

	public Welcome() throws InterruptedException {
		framestart = new JFrame();
		framestart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framestart.setLayout(new BorderLayout());
		ImageIcon background = new ImageIcon("mkt01t0-cmpgn-mysql.jpg");
		Image ii = background.getImage();
		framestart.setSize(new Dimension(ii.getWidth(null), ii.getHeight(null)));
		framestart.setContentPane(new JLabel(background));
		framestart.setLayout(null);
		framestart.setSize(new Dimension(ii.getWidth(null), ii.getHeight(null)));
		label1 = new JLabel("DBJ Explorer");
		Font font = new Font("Agency FB", Font.BOLD, 60);
		label1.setForeground(Color.white);
		label1.setFont(font);
		framestart.add(label1);
		label1.setBounds(50, 50, 500, 100);
		label2 = new JLabel();
		font = new Font("Helvetica", Font.PLAIN, 20);
		label3 = new JLabel("Loading");
		label3.setFont(font);
		framestart.add(label3);
		label3.setBounds(50, 100, 100, 100);
		ImageIcon i = new ImageIcon("hourglass.gif");
		label2.setIcon(i);
		label2.setBounds(130, 100, 100, 100);
		framestart.add(label2, BorderLayout.PAGE_END);
		framestart.setLocationRelativeTo(null);
		framestart.setVisible(true);
		TimeUnit.SECONDS.sleep(1);
		framestart.setVisible(false);
		framestart.dispose();
	}
}