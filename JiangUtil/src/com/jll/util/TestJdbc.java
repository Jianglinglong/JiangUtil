package com.jll.util;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jll.util.jdbc.JdbcMysqlUtil;

public class TestJdbc implements FinalConstants {
	@Test
	public void test() {
		String sql = "delete from teacher where tid = ?";
		String database = JdbcMysqlUtil.DATABASE;
		int rs = JdbcMysqlUtil.runSql(database,sql, 6);
		System.out.println(rs>0?"成功":"失败");
		sql = "update student set sname=?,gender = ? ,class_id=? where sid=?";
		Object[] param = {"赵六","女",1,6};
		System.out.println(JdbcMysqlUtil.runSql(database,sql, param)>0?"更新成功":"更新失败");
	}
	@Test
	public void test2() {
		String sql = "select sid,sname from student where sid = ? or sname=?";
		List<Map<String,Object>> list = JdbcMysqlUtil.select(sql,2,"张三丰");
		Map<String,Object> title = list.get(0);
		System.out.println(title.keySet());
		for(Map<String,Object> row : list) {
			System.out.println(row.values());
		}
	}
}
