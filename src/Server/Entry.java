package Server;

import HttpServer.MyHttpServer;

public class Entry {
	static public void main(String args[]) {
		Core core = new Core();
		core.start();
		new MyHttpServer(core).start();
	}
}
