package global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static final String URL = System.getenv("URL");
	static final String DRIVER = System.getenv("DRIVER");
	static final String ID = System.getenv("ID");
	static final String PW = System.getenv("PW");
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil() {
		try {
			Class.forName(DRIVER);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DBUtil getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, ID, PW);
	}
	
	public void close(AutoCloseable ... acs) {
		for (AutoCloseable  c: acs) {
			if(c != null) {
				try {
					c.close();
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
	}
}