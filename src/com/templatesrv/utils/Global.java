package com.templatesrv.utils;

public class Global {
	public static final Logger LOGGER = new Logger(true);

	public static final String DEFAULT_USERNAME = "admin";
	public static final String DEFAULT_PASSWORD = "root";
	
	public void enableLogPrinting() {
		LOGGER.enablePrinting();
	}
	
	public void disableLogPrinting() {
		LOGGER.disablePrinting();
	}
}
