package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class CSSPage implements Page {
	// Default CSS Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer server, HttpExchange exchange, URLMatch match) {
		Data css = server.readCSS(match.getMatch("CSSFilename"));
		HTTPResponse response = new HTTPResponse(css);
		response.setHeader("Content-Type", "text/css; charset=utf-8");
		return response;
	}

}
