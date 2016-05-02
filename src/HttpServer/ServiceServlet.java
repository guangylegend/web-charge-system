package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Server.Core;
import Server.Request;

public class ServiceServlet extends HttpServlet {
	private int cnt = 0;
	private static final long serialVersionUID = 2L;
	private Core core;
	private String servlet;
	
	public ServiceServlet(Core core, String servlet) throws Exception {
		this.core = core;
		this.servlet = servlet;
    }
	
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		System.out.println("No."+ (++cnt) +" POST "+ TimeString + ' ' + request.getRemoteAddr() + ' ' + request.getRemoteHost());
		
		response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html"); 
    	response.setStatus(HttpServletResponse.SC_OK);
    	
    	Enumeration<String> names = request.getParameterNames();
    	if (names.hasMoreElements()) {
    		Request req = new Request();
    		req.json = names.nextElement();
    		req = new Gson().fromJson(req.json, Request.class);
    		req.date = new Date();
    		req.ip = getIp(request);
    		req.api = servlet;
        	response.getWriter().println(core.handleRequest(req));
    	} else {
    		//
    		System.err.println("input format error");
    	} 
    }
}