package com.templatesrv.base;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.templatesrv.utils.LogLevel;

class RequestHandler implements HttpHandler {
	private TemplateServer server;

	public RequestHandler(TemplateServer ts) {
		this.server = ts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(HttpExchange t) throws IOException {
		String response = "";
		
		try {
			URLMatch url = server.getURLMatch(t.getRequestURI().getPath());
			Class<Page> c = (Class<Page>) Class.forName(url.getURL().getHandler());
			response = c.newInstance().renderResponse(server, t, url);
		} catch (Exception e) {
			server.logger.log(LogLevel.ERROR, e.toString());
			e.printStackTrace();
		}

		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}