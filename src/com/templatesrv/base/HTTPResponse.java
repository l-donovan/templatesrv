package com.templatesrv.base;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
	private byte[] data;
	private Map<String, String> headers;
	private HTTPStatusCode status = HTTPStatusCode.OK;

	public HTTPResponse(byte[] data) {
		this.headers = new HashMap<String, String>();
		this.headers.put("Content-Type", "text/html; charset=utf-8");
		this.data = data;
	}

	public HTTPResponse() {
		this(new byte[]{});
	}

	public HTTPResponse(String data) {
		this(data.getBytes());
	}
	
	public HTTPResponse(Data data) {
		this(data.getData());
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
	
	public void setData(Data data) {
		this.data = data.getData();
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

	public void setStatus(int s) {
		this.status = HTTPStatusCode.getStatusCode(s);
	}
	
	public void setStatus(HTTPStatusCode c) {
		this.status = c;
	}

	public HTTPStatusCode getStatus() {
		return this.status;
	}
	
	public static HTTPResponse redirect(String l) {
		HTTPResponse r = new HTTPResponse();
		r.setHeader("Location", l);
		r.setStatus(HTTPStatusCode.REDIRECT);
		return r;
	}
}
