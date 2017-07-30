package com.templatesrv.base;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.templatesrv.utils.Global;

class RequestHandler implements HttpHandler {
	private TemplateServer server;

	public RequestHandler(TemplateServer ts) {
		this.server = ts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		HTTPResponse response = null;
		URLMatch match = null;
		
		try {
			match = server.getURLMatch(exchange.getRequestURI().getPath());
			if (match.getURL().equals(server.get404URL()))
				Code.throwCode(Code.HTTP_404);
			Class<Page> c = (Class<Page>) Class.forName(match.getURL().getHandler());
			response = c.newInstance().renderResponse(server, exchange, match);
		} catch (Exception e) {
			Global.LOGGER.error(e.toString());
			e.printStackTrace();
		}

		Global.LOGGER.info(String.format(
				"%s %s >>> %s", 
				exchange.getRequestMethod(), exchange.getRequestURI().getPath(), match.getURL().getHandler()));
		
		for (String s : response.getHeaders().keySet())
			exchange.getResponseHeaders().set(s, response.getHeader(s));
		exchange.sendResponseHeaders(200, response.getData().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getData());
		os.close();
	}
}