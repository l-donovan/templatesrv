package com.templatesrv.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

public class Utils {
	public static String inputStreamToString(InputStream is) {
		try (Scanner s = new Scanner(is)) {
			return s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
	}

	public static String decodeURL(String url, String encoding) {
		try {
			return URLDecoder.decode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return url;
		}
	}

	public static String encodeURL(String url, String encoding) {
		try {
			return URLEncoder.encode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return url;
		}
	}

	public static String[][] getURLParameters(String params, String encoding) {
		params = decodeURL(params, encoding);
		String[] settingPairs = params.split("&");
		String[][] out = new String[settingPairs.length][2];
		for (int i = 0; i < settingPairs.length; i++)
			out[i] = settingPairs[i].split("=");
		return out;
	}
}
