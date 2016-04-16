package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 

public class ServiceServlet extends HttpServlet {
	private int cnt = 0;
 
	private static final long serialVersionUID = 2L;

	public ServiceServlet() { 
    }   
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		cnt++;
		System.out.println("No."+ cnt +" POST "+ TimeString + request.getRemoteAddr());
		response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html"); 
    	response.setStatus(HttpServletResponse.SC_OK); 
    	response.getWriter().println("ready for service"); 
    } 
} 