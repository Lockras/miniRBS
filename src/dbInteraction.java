import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


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
	    System.out.println("Opened");
	}
	
	public static void newUser(String login, String pass){
		Statement stmt = null;
		Random randomGenerator = new Random();
		int id=2;
		try{
			while (isIdInTable(id)){
				id = randomGenerator.nextInt(10000);
			}
			stmt = c.createStatement();
			String sql = "insert into USERS (ID, LOGIN, PASS) values ("+id+", '"+login+"', '"+pass+"'); ";
			stmt.execute(sql);
			stmt.close();
			c.commit();
			c.close();
		}
		catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		System.out.println("Inserted");
	}
	public static boolean isIdInTable(int id){
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from USERS; ");
			while (rs.next()){
				int _id = rs.getInt("ID");
				if (id ==_id){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
