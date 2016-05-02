package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import Server.Core;

public class ServiceServlet extends HttpServlet {
	private int cnt = 0;
	private static final long serialVersionUID = 2L;
	private Core core;
	private String servlet;
	
	public ServiceServlet(Core core, String servlet) throws Exception {
		this.core = core;
		this.servlet = servlet;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		System.out.println("No."+ (++cnt) +" POST "+ TimeString + ' ' + request.getRemoteAddr());
				response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html"); 
    	response.setStatus(HttpServletResponse.SC_OK);
    	
    	Enumeration<String> names = request.getParameterNames();
    	while (names.hasMoreElements()) {
    		String name = names.nextElement();
    		System.out.println(name + " " + request.getParameter(name));
    	}
    	response.getWriter().println("123"); 
    }
}