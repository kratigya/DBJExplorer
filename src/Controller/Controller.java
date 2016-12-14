package Controller;

import java.awt.Dimension;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.App_Data;
import Model.User;
import Model.User_Data;
import View.AdminLoginFrame;
import View.PopUp;

public class Controller {

	private App_Data app;
	private User_Data ud;
	private static AdminLoginFrame frame;

	Controller() {
		app = new App_Data();
	}

	public void setFrame(AdminLoginFrame a) {
		frame = a;
	}

	public AdminLoginFrame getFrame() {
		return frame;
	}

	public boolean cConnectRoot(User u) {
		return app.connectRoot(u);
	}

	public boolean cNewUser(User u) {
		return app.newUser(u);
	}

	public boolean setUser(User u) {
		this.ud = new User_Data();
		return this.ud.setUser(u);
	}

	public boolean cValidUser(User u) {
		return app.validUser(u);
	}

	public void cAddQuery(String query, String type, User u) {
		app.addQuery(query, type, u);
	}

	ResultSet cRetrieveQuery(String type, User u) {
		app.retrieveQuery(type, u);
		return null;
	}

	public int cAddDB(String dbname) {
		User u1 = ud.getUser();
		return app.addDB(u1, dbname);
	}

	public User cGetUser() {
		return ud.getUser();
	}

	public void cConnectDB(String dbname) {
		ud.connectDB(dbname);
	}

	public ResultSet cShowTables() {
		return ud.showTables();
	}

	public void cCreateTable(String s) {
		ud.createTable(s);
	}

	public String cGetDataType(String tbname, String colname) {
		return ud.getDataType(tbname, colname);
	}

	public void cExecuteQuery(String s, String type) {
		ud.executeQuery(s);
		cAddQuery(s, type, ud.getUser());
	}

	public ResultSet cExecuteQueryrs(String s, String type) {
		ResultSet rs =  ud.executeQueryrs(s);
		cAddQuery(s, type, ud.getUser());
		return rs;
	}

	public ResultSet cRetrieveDB() {
		return ud.retrieveDB();
	}

	public ResultSet cDesc(String sql) {
		return ud.desc(sql);
	}

	public void cSetSize(Dimension d) {
		frame.setSize(d);
		frame.setLocationRelativeTo(null);
	}

	public void cShowPopUp(String s) {
		PopUp obj = new PopUp();
		obj.showPopUp(s);
	}

	public void cRemoveandadd(JPanel panel, JPanel panel1, JFrame frame) {
		frame.remove(panel);
		frame.setContentPane(panel1);
		frame.validate();
		frame.repaint();
	}
	
	public ResultSet cretrieveQuery(String type) {
		return app.retrieveQuery(type, ud.getUser());
	}
	
	public void cReleaseresources(){
		ud.Releaseresources();
	}

}