package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class JSPage implements Page {
	// Default Javascript Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer server, HttpExchange exchange, URLMatch match) {
		Data js = server.readJS(match.getMatch("JSFilename"));
		HTTPResponse r = new HTTPResponse(js);
		r.setHeader("Content-Type", "application/javascript; charset=utf-8");
		return r;
	}
}
