package com.templatesrv.utils;

public class JSONAuth {
	private String pathPrepend, pathRoot, username, password;
	public JSONAuth() {
		this.pathPrepend = "";
		this.pathRoot    = "";
		this.username    = "";
		this.password    = "";
	}
	
	public String getPathPrepend() {
		return this.pathPrepend;
	}

	public String getPathRoot() {
		return pathRoot;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
