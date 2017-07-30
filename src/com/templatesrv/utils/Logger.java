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
	
	public void info(String s) {
		LogMessage m = new LogMessage(ErrorLevel.INFO, s);
		this.logs.add(m);
		if (this.shouldPrintLogs)
			System.out.printf("[%s@%d] %s\n", m.getLogLevel().name(), m.getTime(), m.getMessage());
	}
	
	public void warn(String s) {
		LogMessage m = new LogMessage(ErrorLevel.WARN, s);
		this.logs.add(m);
		if (this.shouldPrintLogs)
			System.out.printf("[%s@%d] %s\n", m.getLogLevel().name(), m.getTime(), m.getMessage());
	}
	
	public void error(String s) {
		LogMessage m = new LogMessage(ErrorLevel.ERROR, s);
		this.logs.add(m);
		if (this.shouldPrintLogs)
			System.out.printf("[%s@%d] %s\n", m.getLogLevel().name(), m.getTime(), m.getMessage());
	}
	
	public void fatal(String s) {
		LogMessage m = new LogMessage(ErrorLevel.FATAL, s);
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
