package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class CSSPage implements Page {
	// Default CSS Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer s, HttpExchange h, URLMatch u) {
		Data css = s.readCSS(u.getMatch("CSSFilename"));
		HTTPResponse r = new HTTPResponse(css);
		r.setHeader("Content-Type", "text/css; charset=utf-8");
		return r;
	}

}
