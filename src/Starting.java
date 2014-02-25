public class Starting {

	
	public static void main(String[] args) {
		dbInteraction.dbConn();
		dbInteraction.newUser("admin", "admin");
	}

}
