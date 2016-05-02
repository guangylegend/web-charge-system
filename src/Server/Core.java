package Server;

import java.sql.SQLException;
import java.util.HashSet;

import Common.UserInfo;
import mysqlConnector.DbConnector;
import mysqlConnector.UnknownException;

public class Core extends Thread {
	private HashSet<String> IpWhiteList;
	private DbConnector db;
	
	public String handleRequest(Request req) {
/*		if (!IpWhiteList.contains(request.ip)) {
			response.status = "Fail";
			response.content = "Your IP is " + request.ip + ", which is not in whitelist.";
			return response.toString();
		}
		
		UserInfo user = null;
		try {
			user = db.getUserInfo(request.loginName);
		} catch (SQLException e) {
			return response.setContent("Database error: " + e.getMessage()).toString();
		}

		if (user == null || !user.password.equals(request.password)) {
			return response.setContent("loginName or password is wrong").toString();
		}
		
		if (user.remainedMoney <= 0) {//!!!
			response.status = "";
		}
		return response.toString();*/
		return null;
	}
	
	@Override
	public void run() {
		try {
			db = new DbConnector();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DB connector initilize fail");
			return;
		}
		
		while (true) {
			IpWhiteList = new HashSet<String>();
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
