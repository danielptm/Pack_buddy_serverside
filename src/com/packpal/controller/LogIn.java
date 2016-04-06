package com.packpal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.packpal.model.DataTypeConversion;
import com.packpal.model.DbHandler;
import com.packpal.model.EmailAndPasswordBean;
import com.packpal.model.ProfileBean;

/**
 * 
 * @author daniel
 *
 */
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LogIn() {
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
	 * Receives an EmailAndPasswordBean as  json object. Checks the db to see if the password and email
	 * address match a record, and if they do then that ProfileBean is pulled from the Db.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonpfb;
		DbHandler dbh = new DbHandler();
		DataTypeConversion dtc = new DataTypeConversion();
		InputStream is = request.getInputStream();
		String jsonString = dtc.convertStreamToString(is);
		is.close();
		EmailAndPasswordBean epb = DataTypeConversion.getEmailPasswordBean(jsonString);
		ProfileBean pfb = dbh.logUserIn(epb);
		if(pfb!=null){
			jsonpfb = DataTypeConversion.getJsonStringWithGson(pfb);
		}
		else{
			jsonpfb="False";
		}
		Writer w = response.getWriter();
		w.write(jsonpfb);
		w.flush();
		w.close();
		
		
		
	}
}
