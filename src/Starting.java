public class Starting {
	public static String currentUser;
	public static boolean isAdmin;

	
	public static void main(String[] args) {
		//dbInteraction.dbConn();
		//dbInteraction.newUser("root", "root", "TRUE");
		dbInteraction.userAuth("root", "root");
		System.out.println(currentUser);
		System.out.println(isAdmin);
	}

}
