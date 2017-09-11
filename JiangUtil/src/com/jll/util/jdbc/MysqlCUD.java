package com.jll.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlCUD implements ConstantMySql {
	/**
	 * 使用静态代码块加载MySql驱动
	 */
	static{
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySql驱动失败");
			e.printStackTrace();
		}
	}
	/**
	 * 根据数据库名称、密码建立MySql连接对象
	 * @param database  数据库名称
	 * @param pass 用户密码
	 * @return
	 */
	private static Connection getConnection(String database,String pass) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL_NODATABASE+database, CRT_USER, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 获取预编译对象
	 * @param conn 数据库链接对象
	 * @param sql SQL语句
	 * @return pst 预编译对象
	 */
	private static PreparedStatement getPst(Connection conn,String sql) {
		PreparedStatement pst  = null;
		try {
			pst  =  conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pst;
	}
	/**
	 * 执行Sql语句
	 * @param database 数据库名称
	 * @param sql SQL命令，匹配预编译对象格式
	 * @param objects 参数列表
	 * @return row 受影响的行数
	 */
	public static int runSql(String database, String sql , Object ...objects) {
		int row = 0;
		Connection conn = getConnection(database,"123456");
		PreparedStatement pst = getPst(conn, sql);
		try {
			for(int i = 0 ; i < objects.length ; i ++ ) {
				pst.setObject(i+1, objects[i]);
			}
			row = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
}
