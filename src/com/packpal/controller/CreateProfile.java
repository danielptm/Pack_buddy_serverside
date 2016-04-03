package com.packpal.controller;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.packpal.logging.LP;
import com.packpal.model.DataTypeConversion;
import com.packpal.model.DbHandler;
import com.packpal.model.ProfileBean;

public class CreateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profileAddedMessage="";
		System.out.println(request.getContentLength());
		DataTypeConversion dtc = new DataTypeConversion();
		Gson gson = new Gson();
		InputStream is = request.getInputStream();
		String x = dtc.convertStreamToString(is);
		is.close();
		//System.out.println(x);
		System.out.println("Stream converted to string....");
		ProfileBean pfb = gson.fromJson(x, ProfileBean.class);
		DbHandler db = new DbHandler();
		if( ! db.checkIfProfileExists(pfb.getEmail()) ){
			db.writeProfileBeanToDb(pfb.getName(), pfb.getEmail(), pfb.getPassword(), pfb.getHomeCity(), pfb.getImg());
			profileAddedMessage = "Welcome!";
			System.out.println(profileAddedMessage);
		}
		else{
			profileAddedMessage="Profile exists already";
			System.out.println(profileAddedMessage);
		}
		Writer pw = response.getWriter();
		pw.write(profileAddedMessage);
		pw.flush();
		pw.close();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
}





