package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException; 
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Config.Config;
import Server.Core;
import Server.Request;
import Server.RequestInfo;
import Server.Response;
import Tool.Logger;

public class ServiceServlet extends HttpServlet {
	static final long serialVersionUID = 2L;
	
	Core core; //ServerCore
	int serviceId;
	
	public ServiceServlet(Core core, int serviceId) throws Exception {
		this.core = core;
		this.serviceId = serviceId;
    }
	
	/*
	 * get real IP
	 */
	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/*
	 * handle post request
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	RequestInfo info = new RequestInfo();
    	info.ip = getIp(request);
    	info.time = core.getTime();
    	info.serviceId = serviceId;
		
		//response.setCharacterEncoding("UTF-8"); 
		//response.setContentType("text/html;charset=utf-8");
    	
    	/*
    	 * get post json
    	 */
    	ServletInputStream input = request.getInputStream();
    	byte[] bytes = new byte[10000]; //buffer
    	String json = "";
    	while (true) {
    		int length = input.read(bytes);
    		if (length == -1) break;
    		json += new String(bytes, 0, length);
    		if (json.length() > Config.jsonLimitLength) {
    			/*
    			 * requset entity too large
    			 */
    			int code = HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE;
    			response.setStatus(code);
    			Logger.log(new Response(info, code, "request entity too large").toString());
    			return;
    		}
    	}
    	info.json = json;
    	
    	Request req;
    	try {
    		req = Request.fromJson(json);
    	} catch (Exception e) {
    		/*
    		 * request is not json
    		 */
    		int code = HttpServletResponse.SC_FORBIDDEN;
    		response.setStatus(code);
			Logger.log(new Response(info, code, "request is not json").toString());
			return;
    	}

    	Response res = core.handleRequest(req, info);
    	response.setStatus(res.status);
    	if (res.status == HttpServletResponse.SC_OK) {
    		response.getWriter().println(res.content);
    		res.content = "session id " + res.info.sessionId;
    		Logger.log(res.toString());
    	} else {
			Logger.log(res.toString());
		}
    }
}