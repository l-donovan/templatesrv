package com.templatesrv.test.pages;

import com.sun.net.httpserver.HttpExchange;
import com.templatesrv.base.Page;
import com.templatesrv.base.TemplateServer;
import com.templatesrv.base.URLMatch;
import com.templatesrv.utils.FileUtils;

public class FourOhFour implements Page {

	@Override
	public String renderResponse(TemplateServer t, HttpExchange h, URLMatch u) {
		String HTML_LOCATION = t.getSettingOrDefault("HTML_LOCATION", "");
		String html = FileUtils.readFile(HTML_LOCATION + "404.html");
		return html;
	}

}
