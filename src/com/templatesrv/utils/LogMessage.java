package com.templatesrv.utils;

public class LogMessage {
	private LogLevel l;
	private String m;
	private long t;
	
	public LogMessage(LogLevel l, String m) {
		this.l = l;
		this.m = m;
		this.t = System.currentTimeMillis();
	}
	
	public LogLevel getLogLevel() {
		return this.l;
	}
	
	public String getMessage() {
		return this.m;
	}
	
	public long getTime() {
		return this.t;
	}
}
