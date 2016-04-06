package com.packpal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author daniel
 *
 */
public class DbHandler {
	String usersEmail;
	String usersPassword;
	
	String c_name = "name";
	String c_email= "email";
	String c_home_city="home_city";
	String c_password="password";
	String c_profile_picture="profile_picture";

	private final String testPath="jdbc:sqlite:/Users/daniel/desktop/PackPalTestRes/ServletTest2.sqlite";
	private final String productionPath="jdbc:sqlite:/home/daniel/pack_buddy/db/ServletTest2.sqlite";
	
	public DbHandler(){}
	public DbHandler(ProfileAndPassword pfab){
		this.usersEmail=pfab.getEmail();
		this.usersPassword = pfab.getPassword();
	}
	
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
									"VALUES (?, ?, ?, ?, ?);";
		
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
				ps.close();

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
		System.out.println("Wrote item to db");
	}
	// "SELECT * FROM PROFILES WHERE "+c_email+" = "+pfb.getEmail()+";";
	public ProfileBean getProfileFromDb(String pfbEmail){
		PreparedStatement ps;
		ProfileBean pfbToReturn=null;
		Connection con = getConnection();
		ResultSet rs;
		String query1 = "SELECT * FROM PROFILES WHERE email = ?;";
		try {
			ps = con.prepareStatement(query1);
			ps.setString(1, pfbEmail);
			rs = ps.executeQuery();
			rs.next();
			pfbToReturn = new ProfileBean(rs.getString(c_name), rs.getString(c_email), rs.getString(c_home_city), rs.getString(c_password), rs.getString(c_profile_picture));
			rs.close();
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pfbToReturn;
	}
	
	public boolean checkIfProfileExists(String email){
		boolean emailExists=false;
		Connection con = getConnection();
		String query = "SELECT email FROM PROFILES WHERE email = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			emailExists = rs.next();
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emailExists;
	}
	
	public ProfileBean logUserIn(EmailAndPasswordBean epb){
		ProfileBean pfb=null;
		String query = "SELECT * FROM PROFILES WHERE email = ? AND password = ?;";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, epb.getEmail());
			ps.setString(2, epb.getPassword());
			ResultSet rs = ps.executeQuery();
			rs.next();
			pfb = new ProfileBean(rs.getString(c_name), rs.getString(c_email), rs.getString(c_home_city), rs.getString(c_password), rs.getString(c_profile_picture));
			rs.close();
			ps.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return pfb;
	}
	
	public void addEmailAddressToCheckedIn(EmailAndHostel eah){
		String query="INSERT INTO STOCKHOLM_CHECKED_IN ("+eah.getHostel()+") VALUES(?);";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, eah.getEmail());
			ps.executeUpdate();
			con.close();
		}catch(SQLException e){}
		
	}
	
	public ArrayList<String> selectCheckedIn(EmailAndHostel eah){
		
		int lowerLimit=eah.getPageToLoad();
		int numOfRows = 3;
	
		
		ArrayList<String> al = new ArrayList<String>();
		String query="SELECT "+eah.getHostel()+" FROM STOCKHOLM_CHECKED_IN limit ? OFFSET ?";
		Connection con = getConnection();
		if(checkIfHostelExists(eah)){	
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, numOfRows);
				ps.setInt(2, lowerLimit);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					String email = rs.getString(1);
					al.add(email);
				}
				ps.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return al;
	}
	
	public boolean checkIfHostelExists(EmailAndHostel eah){
		boolean hostelExists=false;
		String query="SELECT NAME FROM STOCKHOLM WHERE NAME = ?;";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, eah.getHostel());
			ResultSet rs = ps.executeQuery();
			hostelExists = rs.next();
			rs.close();
			ps.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return hostelExists;
	}
	
	public ArrayList<ProfileBean> selectProfileBeansOfCheckedIn(ArrayList<String> al){
		ProfileBean pfb;
		ArrayList<ProfileBean> alpfb = new ArrayList<ProfileBean>();
		String query="SELECT * FROM PROFILES WHERE email = ?";
		Connection con = getConnection();
		ResultSet rs=null;
		try{
			PreparedStatement ps = con.prepareStatement(query);
			for(String a: al){
					if(checkIfProfileExists(a)){
						ps.setString(1, a);
						rs = ps.executeQuery();
						rs.next();
						pfb = new ProfileBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
						alpfb.add(pfb);
						rs.close();
				}
			}
			ps.close();
			con.close();
			
		}catch(SQLException e){e.printStackTrace();}
		return alpfb;
	}
	
	
	public void removeEmailAddressFromCheckedIn(EmailAndHostel eah){
		
		String query ="DELETE FROM STOCKHOLM_CHECKED_IN WHERE "+eah.getHostel()+" = ?;";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, eah.getEmail());
			ps.executeUpdate();
			ps.close();
			con.close();
		}catch(SQLException e){e.printStackTrace();}
		
	}
	
	/**
	 * valid only for the cbp hostel.
	 * @param email
	 */
	public void removeEmailAddressFromCheckedIn(String email){
		String query ="DELETE FROM STOCKHOLM_CHECKED_IN WHERE CityBackpackers = ?;";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.executeUpdate();
			ps.close();
			con.close();
		}catch(SQLException e){e.printStackTrace();}
	}
	
	public boolean checkIfGuestIsInHostel(EmailAndHostel eah){
		boolean exists=false;
		String query="SELECT "+eah.getHostel()+" FROM STOCKHOLM_CHECKED_IN WHERE "+eah.getHostel()+" = ?;";
		Connection con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, eah.getEmail());
			ResultSet rs = ps.executeQuery();
			exists = rs.next();
			ps.close();
			rs.close();
			con.close();
		}catch(SQLException e){e.printStackTrace();}
		return exists;
	}
}









