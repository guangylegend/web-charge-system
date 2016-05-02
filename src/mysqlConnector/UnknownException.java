package mysqlConnector;

public class UnknownException extends Exception{

	String msg;
	public UnknownException(String string) {
		// TODO Auto-generated constructor stub
		msg = string;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return msg;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1666183849378710640L;
	
}
