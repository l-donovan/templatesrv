package com.templatesrv.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

	public static JSONResponseSettings readJSONSettings(String fileName) {
		return new Gson().fromJson(readFile(fileName), JSONResponseSettings.class);
	}

	public static JSONResponseURLs readJSONURLs(String fileName) {
		return new Gson().fromJson(readFile(fileName), JSONResponseURLs.class);
	}
}
