package HttpServer;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import Server.Core;

public class MyHttpServer extends Thread {
	private Core core;
	private static String[] servletList = {"/faces/recognition/compare",
		                                   "/faces/recognition/verify",
		                                   "/faces/recognition/idverify",
		                                   "/faces/recognition/cardocr"};
	
	public MyHttpServer(Core core) {
		this.core = core;
	}
	
	@Override
	public void run() {
		try {
			Server server = new Server();
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(80);
			server.setConnectors(new Connector[] { connector });
			ServletContextHandler context = new ServletContextHandler();
			context.setContextPath("/");
			context.addServlet(HeartbeatServlet.class, "/heartbeat");
			for (int i = 0; i < servletList.length; ++i) {
				ServiceServlet servlet = new ServiceServlet(core, servletList[i]);
				ServletHolder holder = new ServletHolder(servlet); 
				context.addServlet(holder, servletList[i]);
			}
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
