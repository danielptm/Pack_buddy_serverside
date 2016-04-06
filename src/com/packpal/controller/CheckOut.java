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
import com.packpal.model.EmailAndHostel;

/**
 * 
 * @author daniel
 *
 */
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOut() {
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
		InputStream is = request.getInputStream();
		DataTypeConversion dtc = new DataTypeConversion();
		String x = dtc.convertStreamToString(is);
		EmailAndHostel eah = gson.fromJson(x, EmailAndHostel.class);
		is.close();
		DbHandler dbh = new DbHandler();
		dbh.removeEmailAddressFromCheckedIn(eah);
		Writer w = response.getWriter();
		w.write("Checked out");
		w.flush();
		w.close();
	}
}
