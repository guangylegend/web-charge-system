package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import Server.Core;
import Common.Config;

public class ServiceServlet extends HttpServlet {
	private int cnt = 0;
	private static final long serialVersionUID = 2L;
	private Core core;

	public ServiceServlet() throws Exception {
		if (Config.core == null)
			throw new Exception("ServerCore is NULL");
		this.core = Config.core;
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		cnt++;
		System.out.println("No."+ cnt +" POST "+ TimeString + ' ' + request.getRemoteAddr());
		response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html"); 
    	response.setStatus(HttpServletResponse.SC_OK);
    	int a = Integer.valueOf(request.getParameter("para1"));
    	int b = Integer.valueOf(request.getParameter("para2"));
    	response.getWriter().println(core.sum(a, b)); 
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		cnt++;
		System.out.println("No."+ cnt +" GET "+ TimeString + ' ' + request.getRemoteAddr());
		response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html"); 
    	response.setStatus(HttpServletResponse.SC_OK); 
    	response.getWriter().println("A GET Response"); 
    }
} 