package Model;

import java.sql.*;

import View.PopUp;

public class User_Data {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://localhost:3306/?";
	private User u;
	private Connection conn = null;
	private Statement stmt = null;

	public boolean setUser(User u) {
		this.u = u;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, u.getUname(), u.getPass());
			return true;
		} catch (Exception e) {
			PopUp obj = new PopUp();
			obj.showPopUp("INVALID PASSWORD");
			return false;
		}
	}

	public void createTable(String s) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}

	public User getUser() {
		return u;
	}

	public void connectDB(String dbname) {
		String s = "USE " + dbname + ";";
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(s);
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}

	public ResultSet showTables() {
		try {
			stmt = conn.createStatement();
			String s = "SHOW TABLES;";
			ResultSet rs = stmt.executeQuery(s);
			return rs;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return null;
		}
	}

	public String getDataType(String tbname, String colname) {
		try {
			String d, e;
			ResultSet rs;
			e = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + tbname
					+ "' AND COLUMN_NAME = '" + colname + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(e);
			rs.first();
			d = rs.getString("DATA_TYPE");
			return d;
		} catch (SQLException e1) {
			PopUp obj = new PopUp();
			obj.showPopUp(e1.getMessage(), 1500, 100);
			return null;
		}

	}

	public void executeQuery(String s) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}

	public ResultSet executeQueryrs(String s) {
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			return rs;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return null;
		}
	}

	public ResultSet retrieveDB() {
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			String s = "SHOW DATABASES;";
			rs = stmt.executeQuery(s);
			return rs;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return null;
		}
	}

	public ResultSet desc(String sql) {
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;

		} catch (SQLException e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
			return null;
		}
	}

	public void Releaseresources() {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {
			PopUp obj = new PopUp();
			obj.showPopUp(e.getMessage(), 1500, 100);
		}
	}
}