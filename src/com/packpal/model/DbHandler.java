package com.packpal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbHandler {
	
	//TODO: Make sure that the jarfiles for the sqlite db are in the right place. Otherwise there will be errors Daniel 16.18 Wed!!!!
	
	String c_name = "name";
	String c_email= "email";
	String c_home_city="home_city";
	String c_password="password";
	String c_profile_picture="profile_picture";

	private final String testPath="jdbc:sqlite:/Users/daniel/desktop/PackPalTestRes/ServletTest2.sqlite";
	private final String productionPath="jdbc:sqlite:/home/daniel/pack_buddy/db/ServletTest2.sqlite";
	
	public DbHandler(){}
	
	public Connection getConnection(){
		Connection con;
		try{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(testPath);
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
	public ProfileBean getProfileFromDb(ProfileBean pfb){
		PreparedStatement ps;
		ProfileBean pfbToReturn=null;
		Connection con = getConnection();
		String query = "SELECT * FROM PROFILES WHERE "+c_email+" = "+pfb.getEmail()+";";
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			pfbToReturn = new ProfileBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5
					));
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfbToReturn;
	}
}
