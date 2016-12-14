package Controller;

import java.io.IOException;

import View.AdminLoginFrame;
import View.Welcome;

public class Start {

	public static void main(String[] args) throws IOException, InterruptedException {
		Controller c = new Controller();

		new Welcome();
		AdminLoginFrame obj1 = new AdminLoginFrame();
		c.setFrame(obj1);
		obj1.admin(c);
		obj1.setVisible(true);
	}
}
