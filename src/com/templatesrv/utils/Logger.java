package com.templatesrv.utils;

import java.util.ArrayList;

public class Logger {
	private ArrayList<LogMessage> logs;
	private Boolean shouldPrintLogs;
	private Boolean shouldWriteLogs; // TODO implement
	private LogLevel logLevel;

	public Logger() {
		this.logs = new ArrayList<LogMessage>();
		this.shouldPrintLogs = false;
		this.shouldWriteLogs = false;
		this.logLevel = LogLevel.INFO;
	}

	public Logger(Boolean b) {
		this.logs = new ArrayList<LogMessage>();
		this.shouldPrintLogs = b;
		this.shouldWriteLogs = false;
		this.logLevel = LogLevel.INFO;
	}

	public void log(LogMessage m) {
		this.logs.add(m);
		LogLevel level = m.getLogLevel();
		if (this.shouldPrintLogs && level.ordinal() >= this.logLevel.ordinal())
			System.out.printf("[%-5s@ %d] %s\n", level.name(), m.getTime(), m.getMessage());
		if (this.shouldWriteLogs) {
			System.out.println("This is where the file printing would happen.");
		}
	}

	public void info(String s, Object...o) {
		LogMessage m = new LogMessage(LogLevel.INFO, String.format(s, o));
		this.log(m);
	}

	public void warn(String s, Object...o) {
		LogMessage m = new LogMessage(LogLevel.WARN, String.format(s, o));
		this.log(m);
	}

	public void error(String s, Object...o) {
		LogMessage m = new LogMessage(LogLevel.ERROR, String.format(s, o));
		this.log(m);
	}

	public void fatal(String s, Object...o) {
		LogMessage m = new LogMessage(LogLevel.FATAL, String.format(s, o));
		this.log(m);
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

	public void enableWriting() {
		this.shouldWriteLogs = true;
	}

	public void disableWriting() {
		this.shouldWriteLogs = false;
	}

	public void setLogLevel(LogLevel l) {
		this.logLevel = l;
	}

	public LogLevel getLogLevel() {
		return this.logLevel;
	}
}
