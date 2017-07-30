package com.templatesrv.utils;

public class Global {
	public static final Logger LOGGER = new Logger(true);
	
	public void enableLogPrinting() {
		LOGGER.enablePrinting();
	}
	
	public void disableLogPrinting() {
		LOGGER.disablePrinting();
	}
}
