package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class HTMLPage implements Page {
	// Default HTML Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer s, HttpExchange h, URLMatch u) {
		Data html = s.readHTML(u.getMatch("HTMLFilename"));
		HTTPResponse r = new HTTPResponse(html);
		return r;
	}

}
