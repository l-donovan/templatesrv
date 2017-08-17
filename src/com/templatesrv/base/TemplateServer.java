package com.templatesrv.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.templatesrv.utils.FileUtils;
import com.templatesrv.utils.Global;
import com.templatesrv.utils.JSONAuth;
import com.templatesrv.utils.JSONResponseAuth;
import com.templatesrv.utils.JSONResponseSettings;
import com.templatesrv.utils.JSONResponseURLs;
import com.templatesrv.utils.JSONURL;
import com.templatesrv.utils.TagUtils;

public class TemplateServer {
	private HttpServer server;
	private Map<String, MappedURL> urls;
	private Map<String, String> settings;
	private ArrayList<String> settingsList;
	private MappedURL fourOhFourURL;
	private RequestHandler handler;
	private HttpContext context;

	public TemplateServer(int port) {
		this.urls = new HashMap<String, MappedURL>();
		this.settings = new HashMap<String, String>();
		this.settingsList = new ArrayList<String>();
		
		try {
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
			Global.LOGGER.info("Server has been created on port " + port);
		} catch (IOException e) {
			Code.throwCode(Code.ADDRESS_IN_USE);
		}
		
		this.handler = new RequestHandler(this);
		this.context = server.createContext("/", this.handler);
		
		if (loadSettingsFile())
			Global.LOGGER.info("Settings file has been loaded");

		if (loadURLFile(this.settings.getOrDefault("URLLocation", "res/url.json")))
			Global.LOGGER.info("URLs file has been loaded");

		if (Boolean.parseBoolean(this.settings.getOrDefault("Authentication", "false"))) {
			if (loadAuthFile(this.settings.getOrDefault("AuthLocation", "res/auth.json")))
				Global.LOGGER.info("Auth file has been loaded");
			else
				Global.LOGGER.error("Failed to load auth file");
		} else {
			Global.LOGGER.info("Not loading an auth file");
		}

	}

	public boolean loadSettingsFile() {
		JSONResponseSettings j = FileUtils.readJSONSettings("res/settings.json");
		String v;
		
		for (String k : j.getSettings().keySet()) {
			v = j.getSettings().get(k);
			this.settingsList.add(k);
			this.settings.put(k, v);
			Global.LOGGER.info("Registered setting \"%s\" = \"%s\"", k, v);
		}
		
		return true;
	}

	public boolean loadURLFile(String fileName) {
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
			Global.LOGGER.info("Registered path \"%s\" to \"%s\"", uPath, uHandler);
		}
		
		return true;
	}

	public boolean loadAuthFile(String fileName) {
		JSONResponseAuth j = FileUtils.readJSONAuth(fileName);
		HttpContext aContext;

		for (JSONAuth a : j.getAuth()) {
			String aPath = this.getSettingOrDefault(a.getPathPrepend(), "") + a.getPathRoot();
			String aUsername = a.getUsername();
			String aPassword = a.getPassword();

			// TODO store all httpcontexts so that multiple credentials can be registered for one context
			if (aPath.equals("/")) {
				aContext = this.context;
				Global.LOGGER.warn("Use of root context authentication is not recommended!");
			} else
				aContext = this.server.createContext(aPath, this.handler);

			aContext.setAuthenticator(new BasicAuthenticator("get") {
				@Override
				public boolean checkCredentials(String u, String p) {
					boolean success = u.equals(aUsername) && p.equals(aPassword);
					if (!success)
						Global.LOGGER.warn("Authentication failure");
					return success;
				}
			});
			
			Global.LOGGER.info("Registered auth for \"%s\": %s - %s%s%s",
					aPath,
					aUsername,
					aPassword.substring(0, 1),
					String.join("", Collections.nCopies(aPassword.length() - 2, "*")),
					aPassword.substring(aPassword.length() - 1));
		}
		
		return true;
	}

	public Data readHTML(String fileName) {
		String HTML_LOCATION = this.getSettingOrDefault("HTMLLocation", "  ");
		String html = FileUtils.readFile(HTML_LOCATION.substring(1) + "/" + fileName);
		html = this.replaceDefaultTags(html);
		return new Data(html);
	}

	public Data readCSS(String fileName) {
		String CSS_LOCATION = this.getSettingOrDefault("CSSLocation", "  ");
		String css = FileUtils.readFile(CSS_LOCATION.substring(1) + "/" + fileName);
		return new Data(css);
	}

	public Data readJS(String fileName) {
		String JS_LOCATION = this.getSettingOrDefault("JSLocation", "  ");
		String js = FileUtils.readFile(JS_LOCATION.substring(1) + "/" + fileName);
		return new Data(js);
	}

	public Data readFile(String fileName) {
		String FILE_LOCATION = this.getSettingOrDefault("FileLocation", "  ");
		String file = FileUtils.readFile(FILE_LOCATION.substring(1) + "/" + fileName);
		return new Data(file);
	}

	public String replaceDefaultTags(String html) {
		for (String s : this.settingsList)
			html = TagUtils.replaceTag(html, s, this.getSettingOrDefault(s, ""));

		return html;
	}

	public MappedURL get404URL() {
		return this.fourOhFourURL;
	}

	public String getSetting(String key) {
		return this.settings.get(key);
	}

	public String getSettingOrDefault(String key, String def) {
		return this.settings.getOrDefault(key, def);
	}

	public ArrayList<String> getSettingsList() {
		return this.settingsList;
	}
	
	public void setSetting(String key, String val) {
		this.settings.put(key, val);
		if (!this.settingsList.contains(key))
			this.settingsList.add(key);
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

	public void start() {
		this.server.start();
		Global.LOGGER.info("Server has been started");
	}

	public void stop(Code c) {
		this.server.stop(c.getCode());
		Global.LOGGER.info("Server has been stopped with code %d (%s)", c.getCode(), c);
	}

	public void commandLoop() {
		String q = "";
		Scanner s = new Scanner(System.in);
		while (!q.equals("stop")) {
			System.out.print('$');
			q = s.nextLine();
			Global.LOGGER.info("Received Command: \"%s\"", q);
		}
		s.close();
	}
}
