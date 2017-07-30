package com.templatesrv.utils;

public class JSONURL {
	private String pathPrepend, path, handler, isHome, is404;
	public JSONURL() {
		this.pathPrepend = "";
		this.path = "";
		this.handler = "";
		this.isHome = "";
		this.is404 = "";
	}
	
	public String getPathPrepend() {
		return this.pathPrepend;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getHandler() {
		return this.handler;
	}
	
	public boolean isHome() {
		return this.isHome.equalsIgnoreCase("true");
	}
	
	public boolean is404() {
		return this.is404.equalsIgnoreCase("true");
	}
}
