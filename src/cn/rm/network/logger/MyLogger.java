package cn.rm.network.logger;

public class MyLogger {
	private String logType;
	private String logInfo;

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public MyLogger(String logType, String logInfo) {
		super();
		this.logType = logType;
		this.logInfo = logInfo;
	}

}
