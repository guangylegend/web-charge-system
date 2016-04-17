package mysqlConnector;

public class UserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6600791982711095716L;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "User not found";
	}
	
}
