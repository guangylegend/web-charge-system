package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 

public class HeartbeatServlet extends HttpServlet { 
	private int cnt = 0;
 
	private static final long serialVersionUID = 1L;

	public HeartbeatServlet() { 
    }   
 
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		cnt++;
		System.out.println("No."+ cnt + " HEAD " + TimeString + request.getRemoteAddr());
    	response.setStatus(HttpServletResponse.SC_OK); 
    } 
} 