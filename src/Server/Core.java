package Server;

import java.sql.SQLException;
import java.util.HashSet;

import Common.Request;
import Common.Response;
import Common.UserInfo;
import mysqlConnector.DbConnector;
import mysqlConnector.UnknownException;

public class Core extends Thread {
	private HashSet<String> IpWhiteList;
	private DbConnector db;
	
	public String handleRequest(Request request) {
		Response response = new Response();
		
		if (!IpWhiteList.contains(request.ip)) {
			response.status = "Fail";
			response.content = "Your IP is " + request.ip + ", which is not in whitelist.";
			return response.toString();
		}
		
		UserInfo user = null;
		try {
			user = db.getUserInfo(request.loginName);
		} catch (UnknownException e) {
			return response.setContent("Database error: " + e.getMessage()).toString();
		} catch (SQLException e) {
			return response.setContent("Database error: " + e.getMessage()).toString();
		}

		if (user == null || !user.password.equals(request.password)) {
			return response.setContent("loginName or password is wrong").toString();
		}
		
		if (user.remainedMoney <= 0) {//!!!
			response.status = "";
		}
		return response.toString();
	}
	
	@Override
	public void run() {
		try {
			db = new DbConnector();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		while (true) {
			//IpWhiteList
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
