package com.templatesrv.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.net.httpserver.HttpServer;
import com.templatesrv.utils.FileUtils;
import com.templatesrv.utils.LogLevel;
import com.templatesrv.utils.Logger;

public class TemplateServer {
	private HttpServer server;
	private Map<String, MappedURL> urls;
	private Map<String, String> settings;
	private MappedURL defaultURL;
	private RequestHandler handler;
	
	protected Logger logger;

	public TemplateServer(int port) {
		this.urls = new HashMap<String, MappedURL>();
		this.settings = new HashMap<String, String>();
		this.logger = new Logger(true);

		loadSettingsFile();
		this.logger.log(LogLevel.INFO, "Settings file has been loaded");

		loadURLFile(this.settings.getOrDefault("URLS_XML_LOCATION", "res/url.xml"));
		this.logger.log(LogLevel.INFO, "URLs file has been loaded");
		
		try {
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.logger.log(LogLevel.INFO, "Server has been created on port " + port);
		
		this.handler = new RequestHandler(this);
		server.createContext("/", this.handler);
		
		this.logger.log(LogLevel.INFO, "Server context has been registered");
	}
	
	public void loadSettingsFile() {
		Document doc = FileUtils.readXML("settings.xml");
		NodeList settingsList = doc.getElementsByTagName("setting");
		
		for (int i = 0; i < settingsList.getLength(); i++) {
			Node n = settingsList.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				this.settings.put(
						e.getElementsByTagName("name").item(0).getTextContent(),
						e.getElementsByTagName("value").item(0).getTextContent());
			}
		}
	}

	public void loadURLFile(String fileName) {
		try {
			Document doc = FileUtils.readXML(fileName);
			NodeList urlList = doc.getElementsByTagName("url");

			for (int i = 0; i < urlList.getLength(); i++) {
				Node n = urlList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					String p = e.getElementsByTagName("path").item(0).getTextContent(),
						   h = e.getElementsByTagName("handler").item(0).getTextContent();
					MappedURL url = new MappedURL(p, h);
					NodeList q = e.getElementsByTagName("isdefault");
					if (q.getLength() > 0) {
						if (q.item(0).getTextContent().equals("true"))
							this.defaultURL = url;
					}
					this.urls.put(p, url);
					this.logger.log(LogLevel.INFO, String.format("Registered path \"%s\" to \"%s\"", p, h));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MappedURL getDefaultURL() {
		return this.defaultURL;
	}
	
	public String getSetting(String propertyName) {
		return this.settings.get(propertyName);
	}
	
	public String getSettingOrDefault(String propertyName, String def) {
		return this.settings.getOrDefault(propertyName, def);
	}
	
	public Map<String, MappedURL> getURLs() {
		return this.urls;
	}
	
	public URLMatch getURLMatch(String u) {
		for (String k : this.urls.keySet()) {
			Pattern p = Pattern.compile(this.urls.get(k).getPath());
			Matcher m = p.matcher(u);
			if (!m.matches()) continue;
			String matches[] = new String[m.groupCount()];
			for (int i = 0; i < m.groupCount(); i++)
				matches[i] = m.group(i + 1);
			return new URLMatch(this.urls.get(k), matches);
		}
		return new URLMatch(this.defaultURL, new String[0]);
	}

	public void start() {
		this.server.start();
		this.logger.log(LogLevel.INFO, "Server has been started");
	}

	public void stop(int code) {
		this.server.stop(code);
		this.logger.log(LogLevel.INFO, "Server has been stopped with code " + code);
	}
}
