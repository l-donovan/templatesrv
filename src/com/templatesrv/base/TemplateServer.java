package com.templatesrv.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpServer;
import com.templatesrv.utils.FileUtils;
import com.templatesrv.utils.Global;
import com.templatesrv.utils.JSONResponseSettings;
import com.templatesrv.utils.JSONResponseURLs;
import com.templatesrv.utils.JSONURL;
import com.templatesrv.utils.Logger;
import com.templatesrv.utils.TagUtils;

public class TemplateServer {
	private HttpServer server;
	private Map<String, MappedURL> urls;
	private Map<String, String> settings;
	private ArrayList<String> settingsList;
	private MappedURL fourOhFourURL;
	private RequestHandler handler;

	protected Logger logger;

	public TemplateServer(int port) {
		this.urls = new HashMap<String, MappedURL>();
		this.settings = new HashMap<String, String>();
		this.settingsList = new ArrayList<String>();

		loadSettingsFile();
		Global.LOGGER.info("Settings file has been loaded");

		loadURLFile(this.settings.getOrDefault("URLLocation", "res/url.xml"));
		Global.LOGGER.info("URLs file has been loaded");

		try {
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			Code.throwCode(Code.ADDRESS_IN_USE);
		}

		Global.LOGGER.info("Server has been created on port " + port);

		this.handler = new RequestHandler(this);
		server.createContext("/", this.handler);

		Global.LOGGER.info("Server context has been registered");
	}

	public void loadSettingsFile() {
		JSONResponseSettings j = FileUtils.readJSONSettings("res/settings.json");
		for (String s : j.getSettings().keySet()) {
			this.settingsList.add(s);
			this.settings.put(s, j.getSettings().get(s));
		}
	}

	public void loadURLFile(String fileName) {
		JSONResponseURLs j = FileUtils.readJSONURLs(fileName);
		String uPath, uHandler;
		MappedURL url;
		
		for (JSONURL u : j.getURLs()) {
			uPath = this.getSettingOrDefault(u.getPathPrepend(), "") + u.getPath();
			uHandler = u.getHandler();
			url = new MappedURL(uPath, uHandler);
			if (u.is404())
				this.fourOhFourURL = url;
			this.urls.put(uPath, url);
			Global.LOGGER.info(String.format("Registered path \"%s\" to \"%s\"", uPath, uHandler));
		}
	}

	public String readHTML(String fileName) {
		String HTML_LOCATION = this.getSettingOrDefault("HTMLLocation", "  ");
		String html = FileUtils.readFile(HTML_LOCATION.substring(1) + "/" + fileName);
		html = this.replaceDefaultTags(html);
		return html;
	}

	public String readCSS(String fileName) {
		String CSS_LOCATION = this.getSettingOrDefault("CSSLocation", "  ");
		return FileUtils.readFile(CSS_LOCATION.substring(1) + "/" + fileName);
	}

	public String readJS(String fileName) {
		String JS_LOCATION = this.getSettingOrDefault("JSLocation", "  ");
		return FileUtils.readFile(JS_LOCATION.substring(1) + "/" + fileName);
	}

	public String readFile(String fileName) {
		String FILE_LOCATION = this.getSettingOrDefault("FileLocation", "  ");
		return FileUtils.readFile(FILE_LOCATION.substring(1) + "/" + fileName);
	}

	public String replaceDefaultTags(String html) {
		for (String s : this.settingsList)
			html = TagUtils.replaceTag(html, s, this.getSettingOrDefault(s, ""));

		return html;
	}

	public MappedURL get404URL() {
		return this.fourOhFourURL;
	}

	public String getSetting(String propertyName) {
		return this.settings.get(propertyName);
	}

	public String getSettingOrDefault(String propertyName, String def) {
		return this.settings.getOrDefault(propertyName, def);
	}

	public ArrayList<String> getSettingsList() {
		return this.settingsList;
	}

	public Map<String, MappedURL> getURLs() {
		return this.urls;
	}

	public URLMatch getURLMatch(String u) {
		for (String k : this.urls.keySet()) {
			Pattern p = Pattern.compile(this.urls.get(k).getPath());
			Matcher m = p.matcher(u);
			if (!m.matches())
				continue;
			return new URLMatch(this.urls.get(k), m);
		}
		return new URLMatch(this.fourOhFourURL, null);
	}
	
	public Logger getLogger() {
		return this.logger;
	}

	public void start() {
		this.server.start();
		Global.LOGGER.info("Server has been started");
	}

	public void stop(Code c) {	
		this.server.stop(c.getCode());
		Global.LOGGER.info("Server has been stopped with code " + c.getCode() + " (" + c + ")");
		Code.exitWithCode(c);
	}
	
	public void commandLoop() {
		String q = "";
		Scanner s = new Scanner(System.in);
		while (!q.equals("stop")) {
			q = s.nextLine();
			Global.LOGGER.info("Received Command: \"" + q + "\"");
		}
		s.close();
	}
}
