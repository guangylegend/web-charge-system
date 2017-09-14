package Config;

public class Config {
	static public int jsonLimitLength = 10000000; //1KW
	
	static public String ftpUrl = "";
	static public String ftpUsername = "";
	static public String ftpPassword = "";
	
	static public int loggerSaveTime = 20 * 60 * 1000; //20 minutes
	
	static public int validateTimeout = 3000; //3 seconds
	static public int allocateAlgoMachine = 1000; // 1 second
	static public int callAlgoMachineTimeout = 10000; //10 seconds
	
	static public int serverRefreshDatabaseTime = 10 * 60 * 1000; //10 minutes
}
