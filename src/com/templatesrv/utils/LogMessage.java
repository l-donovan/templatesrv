package com.templatesrv.utils;

public class LogMessage {
	private ErrorLevel l;
	private String m;
	private long t;
	
	public LogMessage(ErrorLevel l, String m) {
		this.l = l;
		this.m = m;
		this.t = System.currentTimeMillis();
	}
	
	public ErrorLevel getLogLevel() {
		return this.l;
	}
	
	public String getMessage() {
		return this.m;
	}
	
	public long getTime() {
		return this.t;
	}
}
