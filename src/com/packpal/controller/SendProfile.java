package com.packpal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packpal.model.DbHandler;
import com.packpal.model.ProfileAndPassword;
import com.packpal.model.ProfileBean;


public class SendProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject jobj = new JsonObject();
		Gson gson = new Gson();
		InputStream is = request.getInputStream();
		String x = convertStreamToString(is);
		ProfileAndPassword pfap = gson.fromJson(x, ProfileAndPassword.class);
		DbHandler db = new DbHandler(pfap);
		//ProfileBean pfb = db.getProfileFromDb(pfap);
//		jobj.addProperty("name", pfb.getName());
//		jobj.addProperty("email", pfb.getEmail());
//		jobj.addProperty("homeCity", pfb.getHomeCity());
//		jobj.addProperty("password", pfb.getPassword());
//		jobj.addProperty("img", pfb.getImg());
		Writer w = response.getWriter();
		w.write(jobj.toString());
		w.close();
		w.flush();
	}
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
    private String convertStreamToString(InputStream is){
    	BufferedReader r = new BufferedReader(new InputStreamReader(is));
    	StringBuilder total = new StringBuilder();
    	String line;
    	try {
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return total.toString();
    }
}
