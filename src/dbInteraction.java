import java.sql.*;


public class dbInteraction {
	
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
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE COMPANY " +
	                   "(ID INTEGER PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " AGE            INT     NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	public static void tblCreate(){
		/*Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE USERS " +
			"(ID INT PRIMARY KEY NOT NULL, " +
			"LOGIN TEXT NOT NULL" +
			"PASS TEXT NOT NULL" +
			"ADMIN INT)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");*/
	}
}
