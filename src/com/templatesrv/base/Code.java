package com.templatesrv.base;

import com.templatesrv.utils.ErrorLevel;
import com.templatesrv.utils.Global;
import com.templatesrv.utils.LogMessage;

public enum Code {
	EXIT_OK				(0, ErrorLevel.INFO,  "OK."),
	
	ADDRESS_IN_USE		(1, ErrorLevel.FATAL, "Failed to bind to port, address already in use!"),
	URL_UNDEFINED		(2, ErrorLevel.ERROR, "URL has no `url.json` entry."),
	HTTP_404			(3, ErrorLevel.ERROR, "404, Page not found.");

	private final int code;
	private final ErrorLevel level;
	private final String description;

	private Code(int code, ErrorLevel level, String description) {
		this.code = code;
		this.level = level;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public ErrorLevel getErrorLevel() {
		return this.level;
	}

	public int getCode() {
		return code;
	}

	public static void throwCode(Code c) {
		Global.LOGGER.log(new LogMessage(c.getErrorLevel(), c.getDescription()));
		if (c.getErrorLevel() == ErrorLevel.FATAL)
			System.exit(c.getCode());
	}
	
	public static void exitWithCode(Code c) {
		throwCode(c);
		System.exit(c.getCode());
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}