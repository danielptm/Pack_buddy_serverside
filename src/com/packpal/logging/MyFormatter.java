package com.packpal.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter{
	
	@Override
	public String format(LogRecord record) {
		StringBuffer sb = new StringBuffer(1000);
		sb.append(record.getMessage()+"\n");
		return sb.toString();
	}

}
