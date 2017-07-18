package com.templatesrv.utils;

import java.util.ArrayList;

public class Logger {
	private ArrayList<LogMessage> logs;
	private Boolean shouldPrintLogs;
	
	public Logger() {
		this.logs = new ArrayList<LogMessage>();
		this.shouldPrintLogs = false;
	}
	
	public Logger(Boolean b) {
		this.logs = new ArrayList<LogMessage>();
		this.shouldPrintLogs = b;
	}
	
	public void log(LogMessage m) {
		this.logs.add(m);
		if (this.shouldPrintLogs)
			System.out.printf("[%s@%d] %s\n", m.getLogLevel().name(), m.getTime(), m.getMessage());
	}
	
	public void log(LogLevel l, String s) {
		LogMessage m = new LogMessage(l, s);
		this.logs.add(m);
		if (this.shouldPrintLogs)
			System.out.printf("[%s@%d] %s\n", m.getLogLevel().name(), m.getTime(), m.getMessage());
	}
	
	public ArrayList<LogMessage> getLogs() {
		return this.logs;
	}
	
	public void enablePrinting() {
		this.shouldPrintLogs = true;
	}
	
	public void disablePrinting() {
		this.shouldPrintLogs = false;
	}
}
