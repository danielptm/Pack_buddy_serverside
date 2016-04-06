package com.packpal.test;


import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import com.google.gson.Gson;
import com.packpal.model.DbHandler;
import com.packpal.model.EmailAndHostel;
import com.packpal.model.ProfileBean;
/**
 * 
 * @author daniel
 *
 */
public class TestCreateProfile {
	
	@Ignore
	public void testDoPost(){

		
		ProfileBean pfb = new ProfileBean("Daniel", "Seattle", "danielptm", "hi", "hi");
		Gson gson = new Gson();
		String x = gson.toJson(pfb);
		ProfileBean xyz = gson.fromJson(x, ProfileBean.class);
		System.out.println(x);
		System.out.println(xyz.getName());
		
	}
	
	@Test
	public void testDb(){
		DbHandler dbh = new DbHandler();
		EmailAndHostel eah = new EmailAndHostel("danielptm@me.com", "CityBackpackers",30);
		
		dbh.addEmailAddressToCheckedIn(eah);
		ArrayList<String> al =  dbh.selectCheckedIn(eah);
		for(String a: al){
			System.out.print("TestLINe "+a+" ");
		}
		ArrayList<ProfileBean>alpfb = dbh.selectProfileBeansOfCheckedIn(al);
		System.out.println(alpfb.size());
		for(ProfileBean pfb: alpfb){
			System.out.print(pfb.getName()+" ");
			System.out.print(pfb.getEmail()+" ");
			System.out.print(pfb.getPassword()+" ");
			System.out.println(pfb.getHomeCity()+" ");
//			System.out.println(pfb.getImg());
			
		}
		//dbh.removeEmailAddressFromCheckedIn(eah);
		
//		ArrayList<String> ab =  dbh.selectCheckedIn(eah);
//		System.out.println(ab.size());
//		for(String a: ab){
//			System.out.println(a);
//		}
//		for(ProfileBean p: alpfb){
//
//			System.out.print(p.getName());
//			System.out.print(p.getEmail());
//			System.out.print(p.getPassword());
//			System.out.print(p.getHomeCity());
////			System.out.print(p.getImg());
//			System.out.println();
//			
//		}
		
	}
	
	@Ignore
	public void testSelectCheckedIn(){
		EmailAndHostel eah = new EmailAndHostel("F", "CityBackpackers", 3);
		DbHandler dbh = new DbHandler();
		dbh.addEmailAddressToCheckedIn(eah);
		ArrayList<String> al = dbh.selectCheckedIn(eah);
		for(String s : al){
			System.out.println(s);
		}
	}
	@Ignore
	public void testremoveEmailAddressFromCheckedIn(){
		DbHandler dbh = new DbHandler();
		dbh.removeEmailAddressFromCheckedIn("C");
	}
}








