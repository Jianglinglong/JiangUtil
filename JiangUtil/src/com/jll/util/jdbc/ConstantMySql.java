package com.jll.util.jdbc;

public interface ConstantMySql {
	String DRIVER_CLASS = PropertiesProcesser.getInstance().getValue("mysql.driver_class");
	
	String LOCALHOST = "127.0.0.1";
	String CRT_USER = PropertiesProcesser.getInstance().getValue("mysql.username");
	
	String CRT_PORT = PropertiesProcesser.getInstance().getValue("mysql.port");
	
	String URL = "jdbc:mysql:/"+LOCALHOST + ":" + CRT_PORT;
}
