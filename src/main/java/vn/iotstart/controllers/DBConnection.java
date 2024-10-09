package vn.iotstart.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static String DB_URL = "jdbc:mysql://localhost:3306/login";
	private static String USER_NAME = "root";
	private static String PASSWORD = "123456";
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static Connection getConnection() throws SQLException, Exception {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
	}
	
	public static void main(String[] args) {
		try {
			new DBConnection();
			System.out.println(DBConnection.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
