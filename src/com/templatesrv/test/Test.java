package com.templatesrv.test;

import java.io.IOException;
import java.util.Scanner;

import com.templatesrv.base.TemplateServer;

public class Test {
	public static void main(String args[]) throws IOException {
		String q = "";
		Scanner s = new Scanner(System.in);
		
		TemplateServer t = new TemplateServer(8000);
		
		t.start();
		while (!q.equals("stop"))
			q = s.nextLine();
		
		t.stop(0);
		s.close();
	}
}
