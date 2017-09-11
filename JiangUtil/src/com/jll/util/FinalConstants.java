package com.jll.util;

import com.jll.util.jdbc.PropertiesProcesser;

public interface FinalConstants {
	public enum MySql implements FinalConstants {
		DRIVER_CLASS(PropertiesProcesser.getInstance().getValue("mysql.driver_class"),1), 
		
		URL(PropertiesProcesser.getInstance().getValue("mysql.url"),2), 
		
		PORT(PropertiesProcesser.getInstance().getValue("mysql.port"),3),
		
		REMOTE_SERVER(PropertiesProcesser.getInstance().getValue("mysql.server"),4),
		
		CRT_USER(PropertiesProcesser.getInstance().getValue("mysql.username"),5),
		
		CRT_PASS(PropertiesProcesser.getInstance().getValue("mysql.password"),6);
		
		private String name;
		private int index;
		
		private MySql(String name,int index) {
			this.index = index;
			this.name = name;
		}
	}
}
