package connectionPool;

public class JDBCConnectionPool {
	private String url;
	private String user;
	private String password;
	private String driver;
	private int maxConnection;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
