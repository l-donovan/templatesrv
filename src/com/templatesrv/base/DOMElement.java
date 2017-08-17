package com.templatesrv.base;

public class DOMElement {
	private String val  = "", tag  = "", body = "", clss = "";
	// TODO implement this class

	public DOMElement() {
	}

	public DOMElement(String tag) {
		this.tag = tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void addClass(String clss) {
		this.clss += clss + ' ';
	}

	public void construct() {
		this.val = String.format("<%s class=\"%s\">%s</%s>", this.tag, this.clss, this.body, this.tag);
	}

	@Override
	public String toString() {
		this.construct();
		return this.val;
	}
}
