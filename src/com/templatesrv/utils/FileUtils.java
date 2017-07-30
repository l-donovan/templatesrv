package com.templatesrv.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.google.gson.Gson;

public class FileUtils {
	public static String readFile(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			System.out.println("Failed to read file: " + fileName);
			e.printStackTrace();
			return null;
		}
	}

	public static Document readXML(String fileName) {
		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONResponseSettings readJSONSettings(String fileName) {
		return new Gson().fromJson(readFile(fileName), JSONResponseSettings.class);
	}
	
	public static JSONResponseURLs readJSONURLs(String fileName) {
		return new Gson().fromJson(readFile(fileName), JSONResponseURLs.class);
	}
}
