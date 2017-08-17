package com.templatesrv.base;

import com.templatesrv.utils.LogLevel;
import com.templatesrv.utils.Global;
import com.templatesrv.utils.LogMessage;

public enum Code {
	EXIT_OK				(0, LogLevel.INFO,  "OK."),
	
	ADDRESS_IN_USE		(1, LogLevel.FATAL, "Failed to bind to port, address already in use!"),
	URL_UNDEFINED		(2, LogLevel.ERROR, "URL has no `url.json` entry.");

	private final int code;
	private final LogLevel level;
	private final String description;

	private Code(int code, LogLevel level, String description) {
		this.code = code;
		this.level = level;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public LogLevel getErrorLevel() {
		return this.level;
	}

	public int getCode() {
		return code;
	}

	public static void throwCode(Code c) {
		Global.LOGGER.log(new LogMessage(c.getErrorLevel(), c.getDescription()));
		if (c.getErrorLevel() == LogLevel.FATAL)
			System.exit(c.getCode());
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}