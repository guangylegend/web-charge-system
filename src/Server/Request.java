package Server;

import java.util.Date;
import java.util.Map;

public class Request {
	public class SessionInfo {
		public String user;
		public String password;
		public String sessionId;
	}
	
	public SessionInfo session_info;
	//public Map<String, Object> image_info, options_info;
	public String ip;
	public Date date;
	public String api;
	public String json;
}
