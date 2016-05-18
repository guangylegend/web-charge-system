package Server;

import Tool.Logger;

public class Response {
	public RequestInfo info;
	public int status; //for ServiceServlet to set http response status
	public String content; //message to response or log
	
	public Response(RequestInfo info, int status, String Content) {
		this.info = info;
		this.status = status;
		this.content = Content;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s access service %d, code %d: %s",
				info.time, info.ip, info.serviceId, status, content);
	}
}
