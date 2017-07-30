package com.templatesrv.base;

import java.util.regex.Matcher;

public class URLMatch {
	private MappedURL url;
	private Matcher matcher;
	
	public URLMatch(MappedURL u, Matcher m) {
		this.url = u;
		this.matcher = m;
	}
	
	public MappedURL getURL() {
		return this.url;
	}
	
	public Matcher getMatcher() {
		return this.matcher;
	}
	
	public String getMatch(String name) {
		return this.matcher.group(name);		
	}
	
	public String getMatch(int index) {
		return this.matcher.group(index);
	}
}
