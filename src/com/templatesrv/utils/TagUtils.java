package com.templatesrv.utils;

import java.util.regex.Pattern;

import com.templatesrv.base.URLMatch;

public class TagUtils {

	public static String replaceTag(String html, String tag, URLMatch u) {
		return Pattern.compile("\\{\\$ " + tag + " \\$\\}").matcher(html).replaceAll(u.getMatch(tag));
	}
	
	public static String replaceTag(String html, String tag, String r) {
		return Pattern.compile("\\{\\$ " + tag + " \\$\\}").matcher(html).replaceAll(r);
	}
}
