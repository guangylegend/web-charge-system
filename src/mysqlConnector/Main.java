package mysqlConnector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;

import Common.UserInfo;

public class Main {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		
		DbConnector c = new DbConnector();
		
		c.Test();
		
	}

}
