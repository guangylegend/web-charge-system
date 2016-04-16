package mysqlConnector;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		DbConnector c = new DbConnector();
		
		c.Test();
	}

}
