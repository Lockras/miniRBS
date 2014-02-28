import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class dbInteraction {
	public static Connection c = null;
	
	public static boolean userAuth(String lgn, String pswd){
		Statement stmt = null;
		try {
			dbConn();
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from USERS; ");
			while (rs.next()){
				String _login = rs.getString("LOGIN");
				if (lgn.equals(_login)){
					String _pswd = rs.getString("PASS");
					if (pswd.equals(_pswd)){
						System.out.println("Login successfully");
						c.close();
						Starting.currentUser = lgn;
						Starting.isAdmin = rs.getBoolean("ADMIN");
						return true;
					}
					else {
						System.out.println("Password is incorrect");
						return false;
					}
				}
			}
			rs.close();
			c.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Login not found");
		return false;
	}
	
	public static void dbConn(){
	    try {
	    	c = null;
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:test.db");
	    	c.setAutoCommit(false);
	    } 
	    catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened");
	}
	
	public static void newUser(String login, String pass){
		Statement stmt = null;
		Random randomGenerator = new Random();
		int id=2;
		try 
		{
			dbConn();
		    System.out.println("Opened");
		    if (isLoginInTable(login)){
		    	System.out.println("User already exists");
		    	c.commit();
		    	c.close();
		    	return;
		    }
		    else {
		    	while (isIdInTable(id)){
					id = randomGenerator.nextInt(10000);
				}
		    }
		    System.out.println("No such user, adding...");
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
		finally {
			try{
				c.close();
			}
			catch (SQLException e){
				
			}
		}
		System.out.println("Inserted");
	}
	
	public static boolean isIdInTable(int id){ //return true if id already exists in db
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
			rs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isLoginInTable(String login){ //return true if login already exists in db
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from USERS; ");
			while (rs.next()){
				String _login = rs.getString("LOGIN");
				if (login.equals(_login)){
					return true;
				}
			}
			rs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
