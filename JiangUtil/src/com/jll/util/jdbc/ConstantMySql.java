package com.jll.util.jdbc;

/**
 * 
    * @ClassName: ConstantMySql
    * @Description: MySql数据库链接常量
    * @author 姜玲珑
    * @date 2017年9月11日
    *
 */

public interface ConstantMySql {
	String DRIVER_CLASS = PropertiesProcesser.getInstance().getValue("mysql.driver_class");

	String LOCALHOST = "127.0.0.1";
	String ROMOTE_SERVER  = PropertiesProcesser.getInstance().getValue("mysql.server");
	
	String CRT_USER = PropertiesProcesser.getInstance().getValue("mysql.username");
	String CRT_PASSWORD = PropertiesProcesser.getInstance().getValue("mysql.password");
	
	String CRT_PORT = PropertiesProcesser.getInstance().getValue("mysql.port");

	String URL_NODATABASE = "jdbc:mysql://" + LOCALHOST + ":" + CRT_PORT + "/";
}
