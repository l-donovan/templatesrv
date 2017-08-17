package com.templatesrv.base;

import com.sun.net.httpserver.HttpExchange;

public class FilePage implements Page {
	// Default File Handler

	@Override
	public HTTPResponse renderResponse(TemplateServer server, HttpExchange exchange, URLMatch match) {
		String filename = match.getMatch("Filename"),
			   MIME     = "application/octet-stream";
		Data file = server.readFile(filename);

		if (filename.endsWith(".ico"))
			MIME = "image/x-icon";
		HTTPResponse r = new HTTPResponse(file);
		r.setHeader("Content-Type", MIME + "; charset=utf-8");
		return r;
	}

}
