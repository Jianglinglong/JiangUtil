package com.jll.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcMysqlUtil implements ConstantMySql {
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;
	/**
	 * 使用静态代码块加载MySql驱动
	 */
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySql驱动失败");
			e.printStackTrace();
		}
	}

	/**
	 * 根据数据库名称、密码建立MySql连接对象
	 * 
	 * @param database
	 *            数据库名称
	 * @param pass
	 *            用户密码
	 * @return
	 */
	private static Connection getConnection(String database, String pass) {
		try {
			conn = DriverManager.getConnection(URL_NODATABASE + database, CRT_USER, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 根据配置文件获取默认连接
	 */
	private static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, CRT_USER, CRT_PASSWORD);
		} catch (Exception e) {
		}
		return conn;
	}

	/**
	 * 获取预编译对象
	 * 
	 * @param conn
	 *            数据库链接对象
	 * @param sql
	 *            SQL语句
	 * @return pst 预编译对象
	 */
	private static PreparedStatement getPst(Connection conn, String sql) {
		try {
			if (conn != null) {
				pst = conn.prepareStatement(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pst;
	}

	/**
	 * 执行Sql语句
	 * 
	 * @param database
	 *            数据库名称
	 * @param sql
	 *            SQL命令，匹配预编译对象格式
	 * @param objects
	 *            参数列表
	 * @return row 受影响的行数
	 */
	public static int runSql(String database, String sql, Object... objects) {
		int row = 0;
		getConnection(database, "123456");
		getPst(conn, sql);
		try {
			for (int i = 0; i < objects.length; i++) {
				pst.setObject(i + 1, objects[i]);
			}
			row = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return row;
	}

	/**
	 * 
	 * @Title: runSql
	 *  @Description: 使用配置文件执行Sql语句
	 *   @param sql 
	 *   @param param 
	 *   @return int 受影响的行数
	 *   @throws SQLException
	 * 
	 */
	public static int runSql(String sql, Object... param) {
		return runSql(DATABASE, sql, param);
	}

	public static List<Map<String, Object>> select(String sql, Object... param) {
		getConnection(DATABASE, CRT_PASSWORD);
		getPst(conn, sql);
		List<Map<String, Object>> rsTable = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0; i < param.length; i++) {
				pst.setObject(i + 1, param[i]);
			}
			rs = pst.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsm = rs.getMetaData();
				while (rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						row.put(rsm.getColumnName(i), rs.getObject(i));
					}
					rsTable.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return rsTable;
	}

	private static void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
