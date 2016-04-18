package Server;

import HttpServer.MyHttpServer;
import Common.Config;

public class Entry {
	static public void main(String args[]) {
		Config.core = new Core();
		Config.core.start();
		new MyHttpServer().start();
	}
}
