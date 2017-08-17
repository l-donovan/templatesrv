package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public interface Page {
	public HTTPResponse renderResponse(TemplateServer server, HttpExchange exchange, URLMatch match);
}
