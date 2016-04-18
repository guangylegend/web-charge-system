package Server;

import java.util.HashSet;
import Common.Request;
import Common.Response;

public class Core extends Thread {
	public int sum(int a, int b) {
		return a + b;
	}
	
	private HashSet<String> IpWhiteList;
	
	public String handleRequest(Request request) {
		Response response = new Response();
		if (!IpWhiteList.contains(request.ip)) {
			response.status = "Fail";
			response.content = "Your IP is " + request.ip + ", which is not in whitelist. Please contact staffs for help.";
			return response.toString();
		}
		//User user = DB.getUserInfo(request.username);
		//if (user == null || !user.password.equals(request.password)) {
		//	response.status = "Fail";
		//	response.content = "Username or password is wrong"
		//	return response.toString();
		//}
		
		return response.toString();
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
