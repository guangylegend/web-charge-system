package HttpServer;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;  


public class MyHttpServer {  
	  
	    public static void main(String[] args) {  
	        try {  
	            // 进行服务器配置  
	            Server server = new Server();   
	            ServerConnector connector = new ServerConnector(server);
		        connector.setPort(8081);
		        server.setConnectors(new Connector[] { connector });
		        ServletContextHandler context = new ServletContextHandler();
	            context.setContextPath("/");  
	            context.addServlet(HeartbeatServlet.class, "/heartbeat"); 
	            context.addServlet(ServiceServlet.class, "/service"); 
	            //context.setResourceBase(".");  
	            //context.setClassLoader(Thread.currentThread().getContextClassLoader());  
	            HandlerCollection handlers = new HandlerCollection();
		        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
		        server.setHandler(handlers);
	            //context.setHandler(new HeartbeatHandler());  
	            server.start();  
	            server.join();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	    }

}  
