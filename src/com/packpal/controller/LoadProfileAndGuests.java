package com.packpal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.packpal.model.DataTypeConversion;
import com.packpal.model.DbHandler;
import com.packpal.model.EmailAndHostel;
import com.packpal.model.ProfileBean;


public class LoadProfileAndGuests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadProfileAndGuests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProfileBean userProfile;
		Gson gson = new Gson();
		DbHandler dbh = new DbHandler();
		DataTypeConversion dtc = new DataTypeConversion();
		InputStream is =request.getInputStream();
		String jsonString = dtc.convertStreamToString(is);
		is.close();
		EmailAndHostel eah = gson.fromJson(jsonString, EmailAndHostel.class);
		userProfile = dbh.getProfileFromDb(eah.getEmail());
		if( ! dbh.checkIfGuestIsInHostel(eah) ){
			dbh.addEmailAddressToCheckedIn(eah);
		}
		ArrayList<String> al = dbh.selectCheckedIn(eah);
		ArrayList<ProfileBean> alpfb = dbh.selectProfileBeansOfCheckedIn(al);
		alpfb.add(0, userProfile);
		String x = gson.toJson(alpfb);
		System.out.println("Output stream: "+x);
		Writer w = response.getWriter();
		w.write(x);
		w.flush();
		w.close();
	
	}
}
