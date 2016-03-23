package com.packpal.logging;


import java.io.IOException;
import java.util.logging.*;

public class LP {
	 String testEnv = "/users/daniel/desktop/PackPalTestRes/Pack_buddy.log";
	 String productionEnvironment="/home/daniel/pack_buddy/logs/Pack_buddy.log";
	 FileHandler fh;
	 private Logger logger;
	 public LP(){
		 logger = Logger.getLogger(LP.class.getName());
			FileHandler fh;
			MyFormatter mf = new MyFormatter();
			try {
				fh = new FileHandler(testEnv, true);
				fh.setFormatter(mf);
				logger.addHandler(fh);
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 public void logInfo(String msg){
		 logger.info(msg); 
	 }
	
	 public Logger getLogger(){
		 return logger;
	 }
	 public FileHandler getFh(){
		 return fh;
	 }

}
