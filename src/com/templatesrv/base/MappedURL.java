package com.templatesrv.base;

public class MappedURL {
	private String path;
	private String handler;
	private Boolean isDefault;
	private Boolean isHome;
	
	public MappedURL(String path, String handler) {
		this.path = path;
		this.handler = handler;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	public void setIsDefault(Boolean b) {
		this.isDefault = b;
	}
	
	public void setIsHome(Boolean b) {
		this.isHome = b;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getHandler() {
		return this.handler;
	}
	
	public Boolean getIsDefault() {
		return this.isDefault;
	}
	
	public Boolean getIsHome() {
		return this.isHome;
	}
}
