package com.packpal.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.packpal.model.DataTypeConversion;
import com.packpal.model.DbHandler;
import com.packpal.model.ProfileBean;

/**
 * 
 * @author daniel
 *
 */
public class CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String testPath = "/Users/daniel/Desktop/PackPalTestRes/CheckedIn.json";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIn() {
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
		Gson gson = new Gson();
		DbHandler dbh = new DbHandler();
		InputStream is = request.getInputStream();
		response.setContentType("application/json");
		DataTypeConversion dtc = new DataTypeConversion();
		String email = dtc.convertStreamToString(is);
		ProfileBean pfb = dbh.getProfileFromDb(email);
		String jsonObj = gson.toJson(pfb, ProfileBean.class);
		Writer w = response.getWriter();
		w.write(jsonObj);
		w.flush();
		w.close();
			
	}
	
}





