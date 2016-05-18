package Server;

import java.util.Map;

public class Request {
	public class SessionInfo {
		public String user;
		public String password;
	}
	
	public SessionInfo session_info;
	public Map<String, Object> image_info, options_info;
	
	public String toJson() {
		return GsonUtils.getGson().toJson(this);
	}
	
	static public Request fromJson(String json) {
		return GsonUtils.getGson().fromJson(json, Request.class);
	}
}
