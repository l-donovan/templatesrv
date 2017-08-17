package com.templatesrv.base;

import com.templatesrv.utils.Global;

public enum HTTPStatusCode {
	OK				(200, "OK"),
	REDIRECT		(302, "Redirect Found"),
	NOT_FOUND		(404, "Not Found");

	private final int code;
	private final String description;

	private HTTPStatusCode(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static HTTPStatusCode getStatusCode(int code) {
		HTTPStatusCode[] vals = HTTPStatusCode.values();
		for (HTTPStatusCode val : vals) {
			if (val.getCode() == code) return val;
		}
		return null;
	}
	
	public static void throwCode(HTTPStatusCode c) {
		Global.LOGGER.warn(String.format("HTTP Code %d - %s", c.getCode(), c.getDescription()));
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}