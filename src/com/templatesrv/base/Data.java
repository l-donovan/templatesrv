package com.templatesrv.base;

import com.templatesrv.utils.TagUtils;

public class Data {
	private byte[] data;

	public Data() {
	}

	public Data(String str) {
		this.data = str.getBytes();
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void setData(String str) {
		this.data = str.getBytes();
	}

	public Data replaceTag(String tag, String val) {
		this.data = TagUtils.replaceTag(new String(this.data), tag, val).getBytes();
		return this;
	}
	
	public Data replaceTags(String tag[], String val[]) {
		assert tag.length == val.length;
		for (int i = 0; i < tag.length; i++)
			this.data = TagUtils.replaceTag(new String(this.data), tag[i], val[i]).getBytes();
		return this;
	}
	
	public Data replaceTags(String tagsVals[]) {
		assert tagsVals.length % 2 == 0;
		for (int i = 0; i < tagsVals.length - 1; i++)
			this.data = TagUtils.replaceTag(new String(this.data), tagsVals[i], tagsVals[i + 1]).getBytes();
		return this;
	}
}
