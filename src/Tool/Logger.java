package Tool;

import Config.Config;

/*
 * auto upload log per Config.loggerSaveTime
 * However, if log's String.size() > loggerLimit, it will also upload
 * 
 */

public class Logger extends Thread {
	static Thread thread;
	static String lock = new String("");
	static String log = "";
	
	public Logger() {
		thread = this;
	}
	
	static void upload() {
		String str;
		synchronized (lock) {
			str = new String(log);
			log = "";
		}
		//upload
	}
	
	static public void log(String msg) {
		System.out.println(msg);
		/*boolean needUpload = false;
		synchronized (lock) {
			log += "msg" + "\n";
			if (log.length() > Config.loggerLimit) {
				needUpload = true;
			}
		}
		if (needUpload) {
			thread.interrupt(); //interrupt unique Logger Thread instance to upload
		}*/
	}
	
	static public void log(String msg, Exception e) {
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(Config.loggerSaveTime);
			} catch (Exception e) {
			}
			upload();
		}
	}
	
	public static void main(String args[]) {
		new Logger().start();
	}
}
