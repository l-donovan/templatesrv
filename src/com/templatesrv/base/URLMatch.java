package com.templatesrv.base;

public class URLMatch {
	private MappedURL url;
	private String[] matches;
	
	public URLMatch(MappedURL u, String[] m) {
		this.url = u;
		this.matches = m;
	}
	
	public MappedURL getURL() {
		return this.url;
	}
	
	public String[] getMatches() {
		return this.matches;
	}
}
