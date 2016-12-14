package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import View.PopUp;

public class App_Data {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://localhost:3306/?";
	private static String USER;
	private static String PASS;

	private Connection conn = null;
	private Statement stmt = null;

	public boolean connectRoot(User u) {
		USER = u.getUname();
		PASS = u.getPass();

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS App_Database");
			stmt.close();
			conn.close();
			DB_URL = "jdbc:mysql://localhost:3306/App_Database";
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS USERLIST (USERID INT NOT NULL AUTO_INCREMENT, USERNAME VARCHAR(30),PASSWORD VARCHAR(30),PRIMARY KEY(USERID));");
			grantPrivilege("App_Database", u);
			stmt.executeUpdate("ALTER TABLE USERLIST AUTO_INCREMENT = 2016000");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS ALLDATABASES (USERID INT,DBNAME VARCHAR(30),FOREIGN KEY (USERID) REFERENCES USERLIST(USERID));");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS SELECT_QUERY (USERID INT,QUERY VARCHAR(500),FOREIGN KEY (USERID) REFERENCES USERLIST(USERID));");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS DELETE_QUERY (USERID INT,QUERY VARCHAR(500),FOREIGN KEY (USERID) REFERENCES USERLIST(USERID));");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS INSERT_QUERY (USERID INT,QUERY VARCHAR(500),FOREIGN KEY (USERID) REFERENCES USERLIST(USERID));");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS UPDATE_QUERY (USERID INT,QUERY VARCHAR(500),FOREIGN KEY (USERID) REFERENCES USERLIST(USERID));");
			grantPrivilege("App_Database", u);
		} catch (SQLException se1) {
			PopUp obj = new PopUp();
			obj.showPopUp(se1.getMessage(), 1500, 100);
			return false;
		} catch (Exception e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return false;
		}
		return true;
	}

	public boolean newUser(User u) {
		try {
			if (validUser(u) == false) {
				String s = "CREATE USER '" + u.getUname() + "'@'localhost' IDENTIFIED BY '" + u.getPass() + "';";
				stmt.executeUpdate(s);
				ResultSet rs = stmt.executeQuery("SELECT * FROM USERLIST");
				if (rs.last()) {
					u.setUID(rs.getInt("USERID") + 1);
				} else {
					u.setUID(2016000);
				}
				stmt.executeUpdate("INSERT INTO USERLIST(USERNAME,PASSWORD) VALUES('" + u.getUname() + "','"
						+ u.getPass() + "');");
				return true;
			} else
				return false;
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return false;
		}
	}

	void grantPrivilege(String s, User u) {
		try {
			stmt = conn.createStatement();
			String s1 = "GRANT ALL PRIVILEGES ON " + s + " . * TO '" + u.getUname() + "'@'localhost';";
			String s2 = "FLUSH PRIVILEGES;";
			stmt.executeQuery(s1);
			stmt.executeQuery(s2);

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}

	public boolean validUser(User u) {
		try {
			stmt = conn.createStatement();
			String s = "SELECT user FROM mysql.user WHERE USER='" + u.getUname() + "';";
			ResultSet rs = stmt.executeQuery(s);
			if (rs.first()) {
				ResultSet rs1 = stmt
						.executeQuery("SELECT USERID FROM USERLIST WHERE USERNAME = '" + u.getUname() + "';");
				rs1.first();
				u.setUID(rs1.getInt("USERID"));
				return true;
			} else {
				
				return false;
			}
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp("INVALID USERNAME");
			return false;
		}
	}

	public void addQuery(String query, String type, User u) {
		try {
			if (type.equals("DEFAULT")) {

			} else {
				stmt = conn.createStatement();
				String s = "INSERT INTO " + type + " VALUES(" + u.getUID() + ",\"" + query + "\");";
				stmt.executeUpdate(s);
			}
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}

	public ResultSet retrieveQuery(String type, User u) {
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			String s = "SELECT QUERY FROM " + type + " WHERE USERID='" + u.getUID() + "';";
			rs = stmt.executeQuery(s);
			return rs;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return null;
		}
	}

	public int addDB(User u, String dbname) {
		try {
			stmt = conn.createStatement();
			String s = "INSERT INTO ALLDATABASES VALUES(" + u.getUID() + ",'" + dbname + "');";
			stmt.executeUpdate(s);

			s = "CREATE DATABASE IF NOT EXISTS " + dbname + ";";
			int i = stmt.executeUpdate(s);
			this.grantPrivilege(dbname, u);
			return i;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return 0;
		}
	}
}