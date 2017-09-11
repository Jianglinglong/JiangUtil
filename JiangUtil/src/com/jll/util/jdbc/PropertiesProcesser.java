package com.jll.util.jdbc;

import java.util.ResourceBundle;

public class PropertiesProcesser {
	private static PropertiesProcesser proPces = null;
	private static ResourceBundle bundle;
	private static ThreadLocal<PropertiesProcesser> threadLocal  = new ThreadLocal<PropertiesProcesser>();

	private PropertiesProcesser() {
		bundle = ResourceBundle.getBundle("com/jll/util/jdbc/mysql");
	}

	public static PropertiesProcesser getInstance() {
		proPces = threadLocal.get();
		if(proPces==null) {
			proPces = new PropertiesProcesser();
			threadLocal.set(proPces);
		}
		return proPces;
	}
	
	public String getValue(String key) {
		return bundle.getString(key);
	}
	
}
