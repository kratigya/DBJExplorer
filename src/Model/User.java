package Model;

public class User {
	private static int UserID;
	private String UserName;
	private String Password;

	public User(String uname, String pass) {
		UserName = uname;
		Password = pass;
	}

	public String getUname() {
		return UserName;
	}

	int getUID() {
		return UserID;
	}
	
	public String getPass() {
		return Password;
	}

	public void setUID(int uid) {
		UserID = uid;
	}
	
	public void setUname(String uname) {
		UserName = uname;
	}

	public void setPass(String pass) {
		Password = pass;
	}

	@Override
	public String toString() {
		
		return "USERNAME : " + getUname() + " USERID (Generated) : " + getUID();
	}
	
	
}