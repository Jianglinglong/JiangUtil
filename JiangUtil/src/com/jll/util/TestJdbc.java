package com.jll.util;

import org.junit.Test;

import com.jll.util.jdbc.MysqlCUD;

public class TestJdbc {
	@Test
	public void test() {
		String sql = "delete from teacher where tid = ?";
		String database = "test";
		int rs = MysqlCUD.runSql(database,sql, 6);
		System.out.println(rs>0?"成功":"失败");
		sql = "update student set sname=?,gender = ? ,class_id=? where sid=?";
		Object[] param = {"赵六","女",1,6};
		System.out.println(MysqlCUD.runSql(database,sql, param)>0?"更新成功":"更新失败");
	}
}
