import java.sql.*;
import org.sqlite.JDBC;
import org.sqlite.SQLite;


public class dbInteraction {
	public static Connection c = null;
	
	public static void main(String[] args) {
		
		
	}
	
	public static boolean userAuth(String lgn, String pswd){
		boolean isAuth = false;
		if ((lgn == "user") && (pswd == null)){
			isAuth = true;
		}
		return isAuth;
	}
	
	public static void dbConn(){
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public static void newUser(String login, String pass){
		Statement stmt = null;
		try{
			stmt = c.createStatement();
			String sql = "insert into 'USERS' ('ID', 'LOGIN', 'PASS') values (2, 'login', 'pass'); ";
			stmt.execute(sql);
			stmt.close();
			c.commit();
			c.close();
		}
		catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		
	}
}
