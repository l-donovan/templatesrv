package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class FilePage implements Page {
	// Default File Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer s, HttpExchange h, URLMatch u) {
		String filename = u.getMatch("Filename"),
			   file = s.readFile(filename),
			   MIME = "application/octet-stream";
		if (filename.endsWith(".ico"))
			MIME = "image/x-icon";
		HTTPResponse r = new HTTPResponse(file);
		r.setHeader("Content-Type", MIME + "; charset=utf-8");
		return r;
	}

}
