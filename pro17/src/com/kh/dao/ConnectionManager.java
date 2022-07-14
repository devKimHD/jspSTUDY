package com.kh.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionManager {
	public static Connection getConnection() {
		try {
			Context ctx=new InitialContext();
			//"java:comp/env" 공통이름
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
			Connection conn=ds.getConnection();
//			System.out.println("conn"+conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void close(Connection conn) {
		if(conn !=null)try {conn.close();}catch(Exception e) {}
	}
}
