package com.templatesrv.base;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
	private byte[] data;
	private Map<String, String> headers;
	
	public HTTPResponse() {
		this.data = new byte[]{};
		this.headers = new HashMap<String, String>();
		this.headers.put("Content-Type", "text/html; charset=utf-8");
	}
	
	public HTTPResponse(byte[] data) {
		this.headers = new HashMap<String, String>();
		this.headers.put("Content-Type", "text/html; charset=utf-8");
		this.data = data;
	}
	
	public HTTPResponse(String data) {
		this.headers = new HashMap<String, String>();
		this.headers.put("Content-Type", "text/html; charset=utf-8");
		this.data = data.getBytes();
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data.getBytes();
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void setHeader(String header, String value) {
		this.headers.put(header, value);
	}
	
	public String getHeader(String header) {
		return this.headers.get(header);
	}
	
	public Map<String, String> getHeaders() {
		return this.headers;
	}
}
