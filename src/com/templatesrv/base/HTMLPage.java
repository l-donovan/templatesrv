package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class HTMLPage implements Page {
	// Default HTML Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer server, HttpExchange exchange, URLMatch match) {
		Data html = server.readHTML(match.getMatch("HTMLFilename"));
		HTTPResponse response = new HTTPResponse(html);
		return response;
	}

}
