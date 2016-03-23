package com.packpal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DbHandler {
	
	private final String testPath="jdbc:sqlite:/Users/daniel/desktop/ServletTest.sqlite";
	private final String productionPath="jdbc:sqlite:/home/daniel/pack_buddy/db/ServletTest2.sqlite";
	
	DbHandler(){
		
	}
	public Connection getConnection(){
		Connection con;
		try{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(productionPath);
		}catch(Exception e){con=null; e.printStackTrace();}
		return con;
	}
	
	public void writeProfileBeanToDb(String name, String email, String password, String home_city, String profile_picture){
		String insertStatement = "INSERT INTO PROFILES (name, email, home_city, password, profile_picture)"+
									"VALUES (?, ?, ?, ?, ?)";
		
		Connection conToDb = getConnection();
		if(conToDb!=null){
			try {
				PreparedStatement ps = conToDb.prepareStatement(insertStatement);
				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, home_city);
				ps.setString(4, password);
				ps.setString(5, profile_picture);
				ps.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conToDb.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
