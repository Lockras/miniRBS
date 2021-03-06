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
						Starting.isAdmin = rs.getInt("ADMIN")>0;
						System.out.println(rs.getInt("ADMIN")>0);
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
	
	public static void newUser(String login, String pass, String admin){
		Statement stmt = null;
		Random randomGenerator = new Random();
		int id=2;
		try 
		{
			dbConn();
		    if (isLoginInTable(login)){
		    	System.out.println("User already exists");
		    	c.commit();
		    	c.close();
		    	return;
		    }
		    else {
		    	while (isIdInTable(id, "USERS")){
					id = randomGenerator.nextInt(10000);
				}
		    }
		    System.out.println("No such user, adding...");
			stmt = c.createStatement();
			String sql = "insert into USERS (ID, LOGIN, PASS, ADMIN) values ("+id+", '"+login+"', '"+pass+"', '"+admin+"'); ";
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
	
	public static boolean isIdInTable(int id, String table){ //return true if id already exists in db
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from "+table+"; ");
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
	
	public static void newClient(String name, String surname, int num, int ser){//creating of new client
		dbConn();
		Statement stmt = null;
		Random randomGenerator = new Random();
		int id=1;
		try 
		{
		    while (isIdInTable(id, "CLIENT")){
					id = randomGenerator.nextInt(10000);
				}
			stmt = c.createStatement();
			String sql = "insert into CLIENT (ID, NAME, SURNAME, PASSPNUM, PASSPSER) values ("+id+", '"+name+"', '"+surname+"', "+num+", "+ser+"); ";
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
	
	public static void newDeposit(int balance, int val, int num, int branch, int accnum, int clientid, int perc, String cdate){
		dbConn();
		Statement stmt = null;
		Random randomGenerator = new Random();
		int id=1;
		if (!isIdInTable(clientid, "CLIENT")){
			System.out.println("No such client");
			return;
		}
		try {
			while (isIdInTable(id, "DEPACCOUNTS")){
				id = randomGenerator.nextInt(10000);
			}
			stmt = c.createStatement();
			String sql = "insert into DEPACCOUNTS (ID, BALANCE, VAL, VALIDNUM, BRANCH, ACCNUM, CLIENTID, SUMM, PERC, CDATE) values " +
					"("+id+", "+balance+", "+val+", "+num+", "+branch+", "+accnum+",  "+clientid+", "+0+", "+perc+",  "+cdate+"); ";
			stmt.execute(sql);
			stmt.close();
			c.commit();
			c.close();
		}
		catch (Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		finally {
			try{
				c.close();
			}
			catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			    System.exit(0);
			}
		}
		System.out.println("Inserted");
	}
	
	public static Object[][] depList(){
		dbConn();
		Statement stmt = null;
		String[][] result;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from DEPACCOUNTS; ");
			int length =0;
			while (rs.next()){
				length++;
			}
			result = new String[length][1];
			rs = stmt.executeQuery("select * from DEPACCOUNTS; ");
			int i =0;
			while (rs.next()){
				String acc = rs.getString("BALANCE")+"-"+rs.getString("VAL")+"-"+rs.getString("VALIDNUM")+"-";
				while ((acc.length()+rs.getString("BRANCH").length())<16){
					acc+="0";
				}
				acc+=rs.getString("BRANCH")+"-";
				while ((acc.length()+rs.getString("ACCNUM").length())<24){
					acc+="0";
				}
				acc+=rs.getString("ACCNUM");
				result[i][0]=acc;
				i++;
				System.out.println(acc);
			}
			rs.close();
		}
		catch (SQLException e){
			result = new String[1][1];
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		return result;
	}
	
	public static String[] depList(int cliendID){
		dbConn();
		Statement stmt = null;
		String[] result;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from DEPACCOUNTS where CLIENTID = "+cliendID+"; ");
			int length =0;
			while (rs.next()){
				length++;
			}
			result = new String[length];
			rs = stmt.executeQuery("select * from DEPACCOUNTS where CLIENTID = "+cliendID+"; ");
			int i =0;
			while (rs.next()){
				String acc = rs.getString("BALANCE")+"-"+rs.getString("VAL")+"-"+rs.getString("VALIDNUM")+"-";
				while ((acc.length()+rs.getString("BRANCH").length())<16){
					acc+="0";
				}
				acc+=rs.getString("BRANCH")+"-";
				while ((acc.length()+rs.getString("ACCNUM").length())<24){
					acc+="0";
				}
				acc+=rs.getString("ACCNUM");
				result[i]=acc;
				i++;
				System.out.println(acc);
			}
			rs.close();
		}
		catch (SQLException e){
			result = new String[1];
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		return result;
	}
	
	public static void insert(String table, String field, String def){
		dbConn();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "insert into "+table+" ("+field;
			sql += ") values ("+def;
			sql += ");";
			stmt.execute(sql);
			stmt.close();
			c.commit();
			c.close();
		}
		catch (SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public static void insert(String table, String field, String def, String field2, String def2){
		dbConn();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "insert into "+table+" ("+field;
			sql += " ,"+field2; 
			sql += ") values ("+def;
			sql += " ,"+def2;
			sql += ");";
			stmt.execute(sql);
			stmt.close();
			c.commit();
			c.close();
		}
		catch (SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
}
