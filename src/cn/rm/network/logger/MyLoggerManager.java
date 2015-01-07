package cn.rm.network.logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

public class MyLoggerManager extends Thread{
	private static Queue<MyLogger> logs= new ConcurrentLinkedQueue<MyLogger>();
	public static void addRequestAndResponseLogger(MyLogger logger){
		logs.add(logger);
	}

	@Override
	public void run() {
		System.out.println("..............日志线程启动.....................");
		while(true){
			int size = logs.size();
			for (int i = 0; i < size; i++) {
				MyLogger log = logs.poll();
				if(null != log){
					Logger logger = Logger.getLogger(log.getLogType());
					logger.info(log.getLogInfo());
					System.out.println("log-->"+log.getLogInfo());
				}
			}
			int sleepTime = 30*1000;			//30秒写一次
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int tempNum = 1;
	public void initThread(){
		if(tempNum == 1){
			Thread th = new MyLoggerManager();
			th.start();
			tempNum++;
		}
	}
	
	public static void main(String[] args){
//		String logStr = "终端状上报,内容:{ SN="+1+",network="+2+",bankwidth="+3+",activate="+4+" }";
//		MyLogger log = new MyLogger("tmsInfo", logStr);
//		MyLogger request = new MyLogger("HTTPInfoLog", "请球参数年记录");
//		MyLoggerManager.addRequestAndResponseLogger(log);
//		MyLoggerManager.addRequestAndResponseLogger(request);
//		Thread th = new MyLoggerManager();
//		th.start();
		MyLoggerManager  m = new MyLoggerManager();
		m.initThread();
	}
}
