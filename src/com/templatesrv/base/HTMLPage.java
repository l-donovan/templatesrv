package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class HTMLPage implements Page {
	// Default HTML Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer s, HttpExchange h, URLMatch u) {
		String html = s.readHTML(u.getMatch("HTMLFilename"));
		return new HTTPResponse(html);
	}

}
