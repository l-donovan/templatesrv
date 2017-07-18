package com.templatesrv.test.pages;

import com.sun.net.httpserver.HttpExchange;
import com.templatesrv.base.Page;
import com.templatesrv.base.TemplateServer;
import com.templatesrv.base.URLMatch;
import com.templatesrv.utils.FileUtils;

public class Article implements Page {

	@Override
	public String renderResponse(TemplateServer s, HttpExchange h, URLMatch u) {
		String HTML_LOCATION = s.getSettingOrDefault("HTML_LOCATION", "");
		String html = FileUtils.readFile(HTML_LOCATION + "article.html");
		return html;
	}

}
